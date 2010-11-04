/**
All individual sketches extend Sketch
*/

package org.loom.scaffold

import java.awt.image.BufferedImage
import java.awt._
import java.awt.geom._

import org.loom.scene._
import org.loom.utility._

class Sketch(val width: Int, val height: Int) {

   var paused: Boolean = false
   var serialByteReadings: Array[Int] = new Array[Int](Config.quantity-1)//we don't store the first (-5) byte
   var serialStringReading: String = "noreading"//for storing RFID codes
   var backgroundColor: Color = Colors.WHITE
   var overlayColor: Color = new Color(0,0,0,30)
   var axesColor: Color = Colors.BLACK
   var axesStrokeWeight: Float = .5f
   var renderer = new Renderer(Renderer.LINES, .2f, new Color(255,255,255,50), new Color(120,10,3,20))//default

   var drawn: Boolean = false

   def setup(): Unit = {}
   def update():Unit = {}
   def draw(g2D: Graphics2D):Unit = {}

   //paint the panel background
   def drawBackground(g2D: Graphics2D) = {
       g2D.setColor(backgroundColor)
       g2D.fill(new Rectangle2D.Double(0, 0, width, height))
   }
   //paint the panel background with an image
   def drawBackground(g2D: Graphics2D, im: BufferedImage) = {
      g2D.drawImage(im, null, 0, 0)
   }
   //paint the panel background once
   def drawBackgroundOnce(g2D: Graphics2D) = {
       if (!drawn) { drawBackground(g2D); drawn = true }
   }
   //paint the panel background once with an image
   def drawBackgroundOnce(g2D: Graphics2D, im: BufferedImage) = {
       g2D.drawImage(im, null, 0, 0)
   }
   /**
   Draws an overlay semi-transparent rectangle - useful for creating trail effect.
   */
   def drawOverlay(g2D: Graphics2D) = {
       g2D.setColor(overlayColor)
       g2D.fill(new Rectangle2D.Double(0, 0, width, height))
   }

   /**
   draws central axes on the screen
   */
   def drawAxes(g2D: Graphics2D): Unit = {
      g2D.setColor(axesColor)
      g2D.setStroke(new BasicStroke(axesStrokeWeight))
      g2D.drawLine(width/2, 0, width/2, height)
      g2D.drawLine(0, height/2, width, height/2)
   }
   /**
   implemented in MySketch (particularly for rfid events
   */
   def serialEventNotify(): Unit = {
   }

}
