/**
ImageAnalysis
*/

package org.loom.media

import java.awt.image.{BufferedImage, WritableRaster, DataBuffer}
import java.awt._

object ImageAnalysis {
	
   /**
   * For keeping alpha info in a color
   * @param rgb - int rgb version of color (from getRGB())
   * @return a color with alpha
   */
   def getRGBA(rgb: Int): Color = {
      var red: Int = 0
      var green: Int = 0
      var blue: Int = 0
      var alpha: Int = 0
      alpha += ((rgb & 0xff000000) >> 24)
      red += ((rgb & 0x00ff0000) >> 16)
      green += ((rgb & 0x0000ff00) >> 8)
      blue += ((rgb & 0x000000ff))
      new Color(red, green, blue, alpha)
   }
   /**
   * get Float array of RGBA values from a color
   * the normalised array is returned in RGB terms (0-255)
   * @param c - color
   * @return Float array
   */
   def getRGBA(c: Color): Array[Float] = {
      val colArray: Array[Float] = c.getComponents(null);
      for (i <- 0 until colArray.length) {
         colArray(i) = colArray(i)*255
      }
      colArray
   }
	
   /**
   * Gets average red, green and blue values from an array of pixels
   * @param pixels
   * @return [r, g, b]
   */
   def getAverageRGB(pixels: Array[Int]): Array[Int] = {
      val averageRGB: Array[Int] = new Array[Int](4)
      var red: Int = 0
      var green: Int = 0
      var blue: Int = 0
      val tot: Int = pixels.length
      for (i <- 0 until tot) {
         red += ((pixels(i) & 0x00ff0000) >> 16)
         green += ((pixels(i) & 0x0000ff00) >> 8)
         blue += ((pixels(i)& 0x000000ff))
      }
      averageRGB(0) = red/tot
      averageRGB(1) = green/tot
      averageRGB(2) = blue/tot
      averageRGB
   }
   /**
   * returns a grey scale average from an array of pixels
   * @param pixels
   * @return
   */
   def getGrayscaleAverage(pixels: Array[Int]): Int = {
      val rgb: Array[Int] = getAverageRGB(pixels);
      ((rgb(0)+rgb(1)+rgb(2))/3);
   }
   /**
   Takes an image without an alpha channel and returns an image with an
   alpha channel.
   @param im BufferedImage
   @return BufferedImage with an alpha
   */
   def getARGBBufferedImage(im: BufferedImage): BufferedImage = {
      val alphaImage: BufferedImage = new BufferedImage(im.getWidth(), im.getHeight, BufferedImage.TYPE_INT_ARGB)
      val raster: WritableRaster = im.getRaster()
      for (r <- 0 until im.getHeight()) {
         for (c <- 0 until im.getWidth()) {
            val pixel: Array[Int] = new Array[Int](4)
            raster.getPixel(c, r, pixel)
            val alpha: Int = 255
            val red: Int = pixel(1)
            val green: Int = pixel(1)
            val blue: Int = pixel(2)
            val col: Color = new Color(red, green, blue, alpha)
            alphaImage.setRGB(c, r, col.getRGB())
         }
      }
      alphaImage
   }

   /**
   * Gets array of average RGB values from an image
   * The image is treated as a grid of rows and columns
   * @param im
   * @param rows
   * @param cols
   * @return an array of RGB arrays
   */
   def getAverageColors(im: BufferedImage, rows: Int, cols: Int):Array[Array[Int]] = {
      val averageColors: Array[Array[Int]] = new Array[Array[Int]](rows*cols)
      val imWidth: Int = im.getWidth()
      val imHeight: Int = im.getHeight()
      val colWidth: Int = imWidth/cols
      val rowHeight: Int = imHeight/rows
      val tot: Int = colWidth*rowHeight
      var pixels: Array[Int] = new Array[Int](tot)
      for (r <- 0 until rows) {
         for (c <- 0 until cols) {
            pixels = im.getRGB(c*colWidth, r*rowHeight, colWidth, rowHeight, null, 0, colWidth)
            val rgb: Array[Int] = getAverageRGB(pixels)
            averageColors((r*cols)+c) = rgb
         }
      }
      averageColors;
   }
   /**
   * Gets array of average grey scale tones from an image
   * The image is treated as a grid of rows and columns
   * @param im
   * @param rows
   * @param cols
   * @return an array of grey scale tones
   */
   def getAverageGreyscaleTones(im: BufferedImage, rows: Int, cols: Int): Array[Int] = {
      val averageColors: Array[Array[Int]] = getAverageColors(im, rows, cols)
      val averageTones: Array[Int] = new Array[Int](rows*cols)
      for (i <- 0 until averageColors.length) {
         val grey: Int = ((averageColors(i)(0)+averageColors(i)(1)+averageColors(i)(2))/3);
         averageTones(i) = grey;
      }
      averageTones;
   }
}
