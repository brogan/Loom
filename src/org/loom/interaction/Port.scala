/**
Port - serial port (for communicating with Arduino)
*/
package org.loom.interaction

import org.loom.scaffold._
import org.loom.utility._

import java.awt.event._

import gnu.io.CommPortIdentifier
import gnu.io.SerialPort
import gnu.io.SerialPortEvent
import gnu.io.SerialPortEventListener
import java.io.InputStream

import java.io.InputStreamReader
import java.io.Reader
import java.io.BufferedReader
import java.lang.StringBuffer

import java.io.OutputStream

class Port(val interactionManager: InteractionManager) {

    var input: InputStream = null

    var buffer: StringBuffer = new StringBuffer()
    var foundStart: Boolean = false

    var output: OutputStream = null
    val readings: Array[Int] = new Array[Int](Config.quantity)
    var chunkArrayIndex: Int = 0
    resetReadings()

    val Id: CommPortIdentifier  = CommPortIdentifier.getPortIdentifier(Config.port)
    val port: SerialPort  = (Id.open("serial talk", 4000)).asInstanceOf[SerialPort]
    input = port.getInputStream()
    output = port.getOutputStream()
    port.setSerialPortParams(9600,
       SerialPort.DATABITS_8,
       SerialPort.STOPBITS_1,
       SerialPort.PARITY_NONE)
    val listener: SerialPortEventListener = new SerialListener(this).asInstanceOf[SerialPortEventListener]
    port.addEventListener(listener)
    port.notifyOnDataAvailable(true)

    /**
    dataAvailable
    */
    def dataAvailable(event: SerialPortEvent): Unit = {

       val available: Int = input.available()
       val chunkArray: Array[Byte]= new Array[Byte](available)
       input.read(chunkArray, 0, available)
       Config.mode match {
          case "bytes" => processBytes(chunkArray)
          case "ascii" => processASCII(chunkArray)
       }

    }
    /**
    processBytes - for processing sensor readings
    captures an array of sensor readings and passes to interaction manager.
    Tricky dealing with serial data - can't be sure when it is going to arrive
    the input chunkArray can vary in length and so the various values can arrive out of
    the desired order.  Easy enough to deal with a single byte but here we are
    receving an array of bytes from Arduino - for instance 3 photoresistor values.  Instead of just sending
    these three values, we create a fixed start byte of -5 in Arduino that gets placed
    in the first position in the array.  We check that both the length of the array is
    correct (in this example, 4) and that the first byte is equal to -5, if so then
    collect the data, pass to the interaction manager and then on to the Sketch
    */
    def processBytes(chunkArray: Array[Byte]): Unit = {
       val readings: Array[Int] = new Array[Int](Config.quantity-1)
       val len: Int = chunkArray.length
       //println("chunkArray.length: " + chunkArray.length + " zero index: " + chunkArray(0))
       if (len == Config.quantity && chunkArray(0) == -5) {
          for (i <- 1 until Config.quantity) {
             val reading: Int = Formulas.signedByteToInt(chunkArray(i))
             readings(i-1) = reading
          }
          interactionManager.passToSprite(readings)
       }
    }
    /**
    processASCII - for processing RFID
    captures an RFID code string and passes to interaction manager
    */
    def processASCII(chunkArray: Array[Byte]): Unit = {
       var charInt: Int = 0
       for (i <- 0 until chunkArray.length) {
          charInt = chunkArray(i).asInstanceOf[Int]
          val c: Char = charInt.asInstanceOf[Char]
          if(c.toString.equals("F") && !foundStart) {
             foundStart = true;
          }
          if (foundStart) {
             if (buffer.length < Config.quantity-1) {//43
                buffer.append(c)
             } else {
                buffer.append(c)
                val RFID: String = buffer.substring(Config.quantity-9, Config.quantity)//35.44
                println("buffer as string: " + RFID)
                interactionManager.passToSprite(RFID)
                buffer = new StringBuffer()
                foundStart = false
             }
          }
       }
    }


    def resetReadings(): Unit = {
       for(i <- 0 until readings.length) readings(i) = 0
       chunkArrayIndex = 0
    }

    /**
    * This should be called when you stop using the port.
    * This will prevent port locking on platforms like Linux.
    */
    def close(): Unit = {
	if (port != null) {
	   port.removeEventListener();
	   port.close();
	}
    }


}
