/**
Stores display modes for SimpleScreenManager
*/

package org.loom.scaffold

import java.awt._

object Display {

   val displayModes: Array[DisplayMode] = new Array[DisplayMode](7)
   displayModes(0) = new DisplayMode(800, 600, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
   displayModes(1) = new DisplayMode(1024, 768, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
   displayModes(2) = new DisplayMode(1280, 960, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
   displayModes(3) = new DisplayMode(1280, 1024, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
   displayModes(4) = new DisplayMode(1600, 900, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
   displayModes(5) = new DisplayMode(1600, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN)
   displayModes(6) = new DisplayMode(1920, 1200, 32, DisplayMode.REFRESH_RATE_UNKNOWN)

   def getDisplayMode(dim: Dimension): DisplayMode = {
      var displayM: DisplayMode = null
      for (dP <- displayModes) {
         if (dP.getWidth == dim.width && dP.getHeight == dim.height) {
            displayM = dP
         }
      }
      displayM
   }

}
