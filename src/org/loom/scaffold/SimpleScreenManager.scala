/**
The SimpleScreenManager class manages initializing and
displaying full screen graphics modes.
Adapted from Dave Bracken: Java Games Programming
*/

package org.loom.scaffold

import java.awt._
import javax.swing.JFrame

class SimpleScreenManager {

    val environment: GraphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment()
    val device: GraphicsDevice = environment.getDefaultScreenDevice()

    /**
    Enters full screen mode and changes the display mode.
    */
    def setFullScreen(displayMode: DisplayMode, window: JFrame): Unit = {
        window.setUndecorated(true);
        window.setResizable(false);

        device.setFullScreenWindow(window);
        if (displayMode != null && device.isDisplayChangeSupported()) {
            try {
                device.setDisplayMode(displayMode);
            } catch {
                case ioe: IllegalArgumentException => println( "IllegalArgumentException in set full screen (Simple Screen Manager)")
                case e: Exception => println( "Exception in set full screen (Simple Screen Manager)")
                case _ => println( "Some other issue in set full screen (Simple Screen Manager)")
            }
        }
    }


    /**
    Returns the window currently used in full screen mode.
    */
    def getFullScreenWindow(): Window = {
        device.getFullScreenWindow();
    }


    /**
    Restores the screen's display mode.
    */
    def restoreScreen(): Unit = {
        val window: Window = device.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }

}
