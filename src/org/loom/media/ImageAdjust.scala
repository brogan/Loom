/**
ImageAdust
*/

package org.loom.media

import java.awt.image.{BufferedImage, WritableRaster}
import java.awt._

object ImageAdjust {

   /**
   Set transparency of a buffered image - affects overall image in a uniform way.
   Transparency values need to be between 0 (fully transparent) t0 255 (fully opaque)
   Slow to animate.  A minBrightness and maxBrightness value allows some portions of image to retain
   their current alpha settings (remain transparent).  Takes the sum of the red, green and blue
   components and then checks to see if greater than min value and less than max value before
   setting transparency.  Pass in something like 5 and 256 if you want black and near black values
   masked or 0 and 250 if you want white and near white pixels masked.
   @param im BufferedImage
   @param trans transparency value
   @param minBrightness 
   @param maxBrightness 
   */
   def setTransparency(im: BufferedImage, trans: Int, minBrightness: Int, maxBrightness: Int): Unit = {
      val raster: WritableRaster = im.getRaster()

      for (r <- 0 until im.getHeight()) {
         for (c <- 0 until im.getWidth()) {
            val pixel: Array[Int] = new Array[Int](4)
            raster.getPixel(c, r, pixel)
            val red: Int = pixel(0)
            val green: Int = pixel(1)
            val blue: Int = pixel(2)
            val sum: Float = (red+green+blue)/3
            if (sum > minBrightness  && sum < maxBrightness) {
               raster.setPixel(c, r, Array(red, green, blue, trans))
            } else {
               raster.setPixel(c, r, Array(red, green, blue, 0))
            }
         }
      }
   }

}
