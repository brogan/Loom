/**
Takes an image and creates a scaled height map.
Parameters: rows (Int), cols (Int), greyScaleDivisor (Int) - this restricts the possible values via the following formula 255/greyScaleDivisor.
*/

package org.loom.media

import java.awt.image.BufferedImage

class ImageToHeightMap(buff: BufferedImage, val rows: Int, val cols: Int, greyScaleDivisor: Int) {
    
    //get the grey scale array for the image, supplying image and number or rows and cols
    val greyScaleArray: Array[Int] = ImageAnalysis.getAverageGreyscaleTones(buff, rows, cols)
   //for (i <- 0 to greyScaleArray.length-1) println(i + " greyscale: " + greyScaleArray(i))
    
    //create new array of grey scale values that maps to the number of characters in the ascii set (minus 1)
    val heightMap: Array[Int] = restrictGreyScaleValues()
    //restrictedGreyScaleArray.foreach{g => println(g) }

    private def restrictGreyScaleValues(): Array [Int] = {
      val newVals: Array[Int] = new Array[Int](greyScaleArray.length)
      for (i <- 0 to greyScaleArray.length-1) {
         newVals(i) = greyScaleArray(i)/greyScaleDivisor
      }
      return newVals
    }
    
}
