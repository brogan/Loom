/**
InteractionManager
*/
package org.loom.interaction

import java.awt._
import javax.swing._
import org.loom.geometry._
import org.loom.scaffold._
import org.loom.scene._

class InteractionManager(val drawManager: DrawManager) extends JPanel {

   var port: Port = null
   if (Config.serial) {
      port = new Port(this)
   }

   var shiftKey: Boolean = false
   var controlKey: Boolean = false

   var mousePressed: Boolean = false
   var mouseReleased: Boolean = false
   var mouseDragged: Boolean = false
   var mousePosition: Point = new Point(0,0)

   var paused: Boolean = false

   /**
   passToSprite
   Sent from Port when serial data available
   passes in an array of bytes (possibly multiple sensor readings)
   */
   def passToSprite(readings: Array[Int]): Unit = {
       //for(i <- 0 until readings.length) println("reading " + i + ": " + readings(i))
       //println("")
       drawManager.sketch.serialByteReadings = readings
       //drawManager.sketch.serialEventNotify()
   }

   /**
   passToSprite
   Sent from Port when serial data available
   passes in a String (eg RFID)
   */
   def passToSprite(reading: String): Unit = {
       println("interactionManager, serial RFID reading: " + reading)
       drawManager.sketch.serialStringReading = reading
       drawManager.sketch.serialEventNotify()
   }

   /**
   * move left
   * normal: track left, shift: turn left, control: bank left
   */
   def moveLeft(): Unit = {
         if(controlKey) {
            //println("banking left")
            Camera.rotate(new Vector3D(0, 0, Camera.rotateSpeed))
         } else {
            if (shiftKey) {
               //println("turning left")
               Camera.rotate(new Vector3D(0,-Camera.rotateSpeed,0))
            } else {
               //println("tracking left")
               Camera.translate(new Vector3D(-Camera.translateSpeed,0,0))
            }
         }
   }
   /**
   * move right
   * normal: track right, shift: turn right, control: bank right
   */
   def moveRight(): Unit = {
         if(controlKey) {
            //println("banking right")
            Camera.rotate(new Vector3D(0,0,-Camera.rotateSpeed))
         } else {
            if (shiftKey) {
               //println("turning right")
               Camera.rotate(new Vector3D(0,Camera.rotateSpeed,0))
            } else {
               //println("tracking right")
               Camera.translate(new Vector3D(Camera.translateSpeed,0,0))
            }
         }
   }
   /**
   * move up
   * normal: tracking forward, shift: crane up, control: pitch up
   */
   def moveUp(): Unit = {

         if(controlKey) {
            //println("pitch up")
            Camera.rotate(new Vector3D(Camera.rotateSpeed,0,0))
         } else {
            if (shiftKey) {
               //println("crane up")
               Camera.translate(new Vector3D(0,Camera.translateSpeed,0))
            } else {
               //println("tracking forward")
               Camera.translate(new Vector3D(0,0,Camera.translateSpeed))
            }
         }
   }
   /**
   * move down
   * normal: track back, shift: crane down, control: pitch down
   */
   def moveDown(): Unit = {
         if(controlKey) {
            //println("pitch down")
            Camera.rotate(new Vector3D(-Camera.rotateSpeed,0,0))
         } else {
            if (shiftKey) {
               //println("crane down")
               Camera.translate(new Vector3D(0,-Camera.translateSpeed,0))
            } else {
               //println("tracking back")
               Camera.translate(new Vector3D(0,0,-Camera.translateSpeed))
            }
         }
   }
   /**
   * toggles pause animation
   */
   def switchRenderingMode(mode: Int): Unit = {
     drawManager.sketch.renderer.mode = mode
   }
   /**
   * toggles pause animation
   */
   def togglePause(): Unit = {
     paused = !paused
     if (paused) {
        drawManager.sketch.paused = true
     } else {
        drawManager.sketch.paused = false
     }
   }
   /**
   * quit
   */
   def quit(): Unit = {
     System.exit(0)
     if (Config.serial) {
        port.close()
     }
   }

}
