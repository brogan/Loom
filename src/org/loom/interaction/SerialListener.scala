/**
Serial Listener
*/
package org.loom.interaction

import gnu.io.SerialPortEvent
import gnu.io.SerialPortEventListener

class SerialListener(val port: Port) extends SerialPortEventListener {

    override def serialEvent(event: SerialPortEvent): Unit = {
       event.getEventType() match {
          case SerialPortEvent.DATA_AVAILABLE => port.dataAvailable(event)
       }
    }

}
