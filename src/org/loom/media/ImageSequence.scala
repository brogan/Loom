/**
Stores and updates an image sequence.
PLEASE NOTE IMAGE FILE NAMES ARE IN THIS FORMAT: "berg_000000.jpeg", "berg_000001.jpeg", etc. (start at 0)
Parameters: totalImages (Int), frameIncrement(Int - usually 1, currFrame (Int - usually 0), folder holding images (must be in resources/sequences), prefix ("berg_", etc.), extension (".jpeg", ".jpg"), draw location (Vector2D), scale (Vector2D) - images can be scaled as you like.
With height maps, make sure that you get the currentBuffered Image.
*/

package org.loom.media

import java.io.File
import java.awt.{Graphics2D,Image}
import java.awt.image.BufferedImage

import org.loom.geometry._

class ImageSequence (val totImages: Int, var frameInc: Int, var currFrame: Int, var folder: String, var prefix: String, var extn: String, var location: Vector2D, var scale: Vector2D, var looping: Boolean) {

   var sequenceFolderpath: String = ProjectFilePath.getResourceFilePath(ProjectFilePath.SEQUENCE, folder)

   var currentImage: Image = null
   var currentBufferedImage: BufferedImage = null 
   /**
   Update - gets the next image in the sequence, returns back to 0 when the sequence finishes (so loops)
   */
   def update(): Unit = {
      if (currFrame > -1 && currFrame < 10) {
         currentImage = ImageLoader.imageLoad(sequenceFolderpath + ProjectFilePath.separator + prefix + "00000" + currFrame + extn)
      } else if (currFrame > 9 && currFrame < 100) {
         currentImage = ImageLoader.imageLoad(sequenceFolderpath + ProjectFilePath.separator + prefix + "0000" + currFrame + extn)
      } else if (currFrame > 99 && currFrame < 1000) {
         currentImage = ImageLoader.imageLoad(sequenceFolderpath + ProjectFilePath.separator + prefix + "000" + currFrame + extn)
      } else if (currFrame > 999 && currFrame < 10000) {
         currentImage = ImageLoader.imageLoad(sequenceFolderpath + ProjectFilePath.separator + prefix + "00" + currFrame + extn)
      } else if (currFrame > 9999 && currFrame < 100000) {
         currentImage = ImageLoader.imageLoad(sequenceFolderpath + ProjectFilePath.separator + prefix + "0" + currFrame + extn)
      } else if (currFrame > 99999 && currFrame < 1000000) {
         currentImage = ImageLoader.imageLoad(sequenceFolderpath + ProjectFilePath.separator + prefix + currFrame + extn)
      } else {
        println("TOO MANY FRAMES!")
      }
      currentBufferedImage = currentImage.asInstanceOf[BufferedImage]
      if (currFrame < totImages-1) {
         currFrame += frameInc
      } else {
         if (looping) {
            currFrame = 0
         }
      }
   }
   /**
   Draw - draws the currentImage
   @param g2D(Graphics2D)
   */
   def draw(g2D: Graphics2D): Unit = {
      //g2D.drawImage(currentImage, null, (location.x).toInt, (location.y).toInt)
      g2D.drawImage(currentImage.getScaledInstance(1246, 796, Image.SCALE_FAST), location.x.toInt, location.y.toInt, null)
   }
}
