/***************************************************
MySketch
*/

//package declaration
package org.loom.mysketch

//import java library classes
import java.awt.{Color, Graphics2D, BasicStroke, Image}
import java.awt.image.BufferedImage
import java.awt.geom._
import java.awt.Polygon
import java.io.File
import javax.sound.sampled._

//import loom classes
import org.loom.geometry._
import org.loom.interaction._
import org.loom.media._
import org.loom.scaffold._
import org.loom.scene._
import org.loom.utility._

/**
Demos how images can be loaded and displayed sequentially.
And how serial devices can be used to control playback.  Continuous
serial messages (sensor readings and the like) can be dealt with
in the update method via the variable serialByteReadings which stores
the current array of sensor readings.  Discontinuous readings (RFID codes
and the like) are available through serialStringReading.  This value is available
whenever you wish to access it, but best to monitor via the serialEventNotify
method which gets called each time there is a new discontinuous serial event 
(a new RFID code or whatever).
*/
class MySketch(width: Int, height: Int) extends Sketch (width, height) {

   //load a looping sound file
   val soundManager: SoundManager = new SoundManager()
   val soundFilePath: String = ProjectFilePath.getResourceFilePath(ProjectFilePath.SOUND, "space.wav")
   soundManager.addSound(soundFilePath, true, Clip.LOOP_CONTINUOUSLY, 0)

   //create an image sequence
   //PLEASE NOTE IMAGE FILE NAMES ARE IN THIS FORMAT: "berg_000000.jpeg", "berg_000001.jpeg", etc.
   //the sequence needs to be in a folder in the resources/sequences directory
   //parameters: number of images, sequence increment per frame, start index, sequence folder, sequence file name prefix, extension, location, scale, looping
   var imageSequence: ImageSequence = new ImageSequence(13, 1, 0, "swirl", "sw_", ".jpg", new Vector2D(0,0), new Vector2D(1246, 796), true)

   //Stores the current image index from any given image sequence
   var currIm: BufferedImage = null

   //stores the overall sequence index (if you have a number of image sequences
   var currSequenceIndex: Int = 2

   /**
   Setup runs once when the sketch begins
   */
   override def setup(): Unit = {
      backgroundColor = new Color(0,0,0)
   }

   /**
   Update gets called prior to draw each drawing cycle.
   It is the place where you should update animation properties
   prior to drawing.
   */
   override def update(): Unit = {

      if (!paused) {
         /*
         Calculates lowest of the three photoresistor sensor readings.  If the lowest photoresistor reading is not equal to the 
         current sequence index then load the sequence that corresponds to the lowest index.  Then set the currSequenceIndex
         equal to the lowest index in preparation for the next cycle.  The reason for the currSequenceIndex variable is to prevent reloading
         the same sequence over and over each update cycle.
         */
         val least: Int = Formulas.getLeastValueIndex(serialByteReadings)
         //println("least: " + least)
         if (currSequenceIndex != least) {
            least match {
               case 0 => imageSequence = new ImageSequence(17, 1, 0, "random", "ran_", ".jpg", new Vector2D(0,0), new Vector2D(1246, 796), true)
               case 1 => imageSequence = new ImageSequence(15, 1, 0, "square", "sq_", ".jpg", new Vector2D(0,0), new Vector2D(1246, 796), true)
               case 2 => imageSequence = new ImageSequence(13, 1, 0, "swirl", "sw_", ".jpg", new Vector2D(0,0), new Vector2D(1246, 796), true)
            }
            currSequenceIndex = least;
         }

         imageSequence.update()
         currIm = imageSequence.currentBufferedImage//store the current buffered image for drawing and to extract subimages
      }

   }

   /**
   Here is where you draw to the screen
   */
   override def draw(g2D: Graphics2D): Unit = {
      drawBackground(g2D)//to draw repeatedly
      //drawBackgroundOnce(g2D)//to draw just once when the sketch first runs
      if (currIm != null) {
         val xLoc: Int = (width - currIm.getWidth())/2
         val yLoc: Int = (height - currIm.getHeight())/2
         g2D.drawImage(currIm, null, xLoc, yLoc)
      }

   }

   /**
   Tints some portion of the screen by drawing a semi-transparent colored rectangle.
   */
   def tint(g2D: Graphics2D, tint: Color, x: Int, y: Int, w: Int, h: Int) = {
       g2D.setColor(tint)
       g2D.fill(new Rectangle2D.Double(x, y, w, h))
   }


   /**
   serialEventNotify is for discontinuous serial messages - like RFID codes.
   Receives messages sent from Sketch via interaction manager and serial port (in Main)
   */
   override def serialEventNotify(): Unit = {
      println("new rfid reading: " + serialStringReading)

      serialStringReading match {

         case "1500D001B" => imageSequence = new ImageSequence(17, 1, 0, "random", "ran_", ".jpg", new Vector2D(0,0), new Vector2D(1246, 796), true)
         case "1500CFF3B" => imageSequence = new ImageSequence(15, 1, 0, "square", "sq_", ".jpg", new Vector2D(0,0), new Vector2D(1246, 796), true)
         case "1500D0025" => imageSequence = new ImageSequence(13, 1, 0, "swirl", "sw_", ".jpg", new Vector2D(0,0), new Vector2D(1246, 796), true)

      }

   }


}


