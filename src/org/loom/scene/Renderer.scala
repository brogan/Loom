/**
Renderer holds rendering parameters.  You must define all parameters even if you don't use them all.
MODE: one of four rendering modes (points, lines, filled and filled_stroked) expressed as an Int.  Specify mode via the Renderer Object definitions.
STROKEWIDTH: the width of the stroke expressed as Float.
STROKECOLOR: the stroke color (java.awt.Color)
FILLCOLOR: the stroke color (java.awt.Color)
*/

package org.loom.scene

import java.awt.Color

class Renderer (var mode: Int, var strokeWidth: Float, var strokeColor: Color, var fillColor: Color) {

   /**
   Clone the Renderer.  Produces an independent copy.
   */
   override def clone(): Renderer = {
      var sc: Color = new Color(strokeColor.getRed(), strokeColor.getGreen(), strokeColor.getBlue(), strokeColor.getAlpha())
      var fc: Color = new Color(fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(), fillColor.getAlpha())
      new Renderer(mode, strokeWidth, sc, fc)
   }

}

/**
Renderer defines the four rendering modes as names so that you can access a mode by a name.
*/
object Renderer {

   val POINTS: Int = 0
   val LINES: Int = 1
   val FILLED: Int = 2
   val FILLED_STROKED: Int = 3

}
