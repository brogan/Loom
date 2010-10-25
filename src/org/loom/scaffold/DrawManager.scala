/**
DrawManager
*/

package org.loom.scaffold

import java.awt._
import org.loom.mysketch._

class DrawManager() {

   var drawn: Boolean = false

   val sketch: Sketch = new MySketch(Config.width, Config.height)
   sketch.setup()

   def update(): Unit = {
      if (Config.animating) {
         sketch.update()
      }
   }
   def draw(graphics: Graphics): Unit = {
      var g2D: Graphics2D = graphics.asInstanceOf[Graphics2D]
      g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
      if (Config.animating) {
         sketch.draw(g2D)
      } else {
         if (!drawn) sketch.draw(g2D)//draw once
         drawn = true
      }
   }
}
