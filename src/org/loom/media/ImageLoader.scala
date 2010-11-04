/**
Loads an image from a file path
*/

package org.loom.media

import java.awt._
import java.io.File
import java.io.IOException

import javax.imageio.ImageIO

object ImageLoader {

   /**
   Loads an image from a String file path
   @param filePath
   @return an image
   */
   def imageLoad (filePath: String): Image = {
	//println("ImageLoader, filePath: "+filePath);
	try {
		val image: Image = ImageIO.read(new File(filePath))
		//System.out.println("image loaded")
		return image
	} catch {
		case ioe: IOException => println("IOException")
	}
	return null
   }

}
