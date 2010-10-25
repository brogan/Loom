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
Description of sketch goes here.
This sketch displays a flickering, semi-transparent scull
image over the top of a spinning and morphing ground plane.  The latter is displaced by a height map image.
The rendering mode of the height plane can be driven by either an array of photo-resistor values (stored in
serialByteReadings) or by RFID codes (see code currently commented out in serialEventNotify() method).
*/
class MySketch(width: Int, height: Int) extends Sketch (width, height) {

//////////////////////////////////////////////
   val imName: String = "scull_smallc.png"
//////////////////////////////////////////////
   //next lines make scull image semi-transparent
   //the totally black pixels are set to fully transparent so that we just see scull
   val imFilePath: String = ProjectFilePath.getResourceFilePath(ProjectFilePath.IMAGE, imName)
   val im: Image = ImageLoader.imageLoad(imFilePath)
   val imBuff: BufferedImage = im.asInstanceOf[BufferedImage]
   val alphaBuff: BufferedImage = ImageAnalysis.getARGBBufferedImage(imBuff)//make sure image has alpha channel
   var alpha: Int = 0
   //image, alpha, minBrightness, maxBrightness
   ImageAdjust.setTransparency(alphaBuff, alpha, 5, 256)

//////////////////////////////////////////////
   val imageFileName: String = "mountain.jpg"
//////////////////////////////////////////////
   //the mountain image is used for the height map to displace the ground place
   val image: Image = ImageLoader.imageLoad(ProjectFilePath.getResourceFilePath(ProjectFilePath.IMAGE, imageFileName))
   val buff: BufferedImage = image.asInstanceOf[BufferedImage]

   //THE HEIGHT MAP NEEDS TO HAVE ONE MORE ROW AND COLUMN THAN THE GRID PLANE
   //BECAUSE THERE ARE ONE MORE SET OF POINTS FOR EACH ROW AND EACH COLUMN
   //THINK ABOUT IT: A ROW OF FOUR COLUMNS ACTUALLY REQUIRES FIVE POINTS
   val hMap: ImageToHeightMap = new ImageToHeightMap(buff, 33,33, 10)

   //create a renderer
   val rendererGround = new Renderer(Renderer.FILLED_STROKED, .5f, new Color(255,255,0,30), new Color(120,10,3,18))
  
   val gridPlane: Shape3D = ShapeCreator.makeGridPlane(32,32)

   //sets the points to height map y values at the outset
   //not used here because we are doing in update
   //for (i <- 0 until gridPlane.points.size) gridPlane.points(i).y = ((hMap.heightMap(i))+10)

   //set some initial parameters for the shape
   val loc2: Vector3D = new Vector3D(0, -4000, 20000)//initial location
   val size2: Vector3D = new Vector3D(800, 200, 800)//initial scale
   val startRotation2: Vector3D = new Vector3D(0, 0, 0)//initial rotation
   //rotOffset controls where the rotation point is set in the shape
   //so, for example, negative 1 on z brings all the points in the object
   //back by -1, which puts the rotation point on the tip of the crystal
   val rotOffset2: Vector3D = new Vector3D(0, 0, 0)

   //set some animation parameters for the shape
   val scaleFactor2: Vector3D = new Vector3D(1, 1, 1)
   val rotFactor2: Vector3D = new Vector3D(0, 2, 0)
   val speedFactor2: Vector3D = new Vector3D(0, 0, 0)

   //create an animator based on the above parameters
   val animator2: Animator3D = new Animator3D(true, scaleFactor2, rotFactor2, speedFactor2)

   //create a sprite from the shape, initial parameters, animator and renderer
   val groundPlane: Sprite3D = new Sprite3D(gridPlane,loc2,size2,startRotation2,rotOffset2,animator2,rendererGround) 

   //create a notional view
   //parameters: screen width, screen height, view width, view height, border width, border height
   val view3D: View3D = new View3D(width, height, width, height, 0, 0)
   //create a scene
   val scene: Scene3D = new Scene3D()
   //set properties in Camera object: view3D, viewAngle and scene3D
   Camera.view3D = view3D
   Camera.viewAngle = 75
   Camera.scene = scene

   //add groundplane to scene
   scene.addSprite(groundPlane)

   //load some distance dependent sound
   val silentDistanceZ: Int = 75000//set the silent distance for the sound
   val soundManager: SoundManager = new SoundManager()
   val soundFilePath: String = ProjectFilePath.getResourceFilePath(ProjectFilePath.SOUND, "space.wav")
   soundManager.addSound(soundFilePath, true, Clip.LOOP_CONTINUOUSLY, 0)

   //create an image sequence
   //PLEASE NOTE IMAGE FILE NAMES ARE IN THIS FORMAT: "berg_000000.jpeg", "berg_000001.jpeg", etc.
   //the sequence needs to be in a folder in the resources/sequences directory
   //parameters: number of images, sequence increment per frame, start index, sequence folder, sequence file name prefix, extension, location, scale, looping
   var imageSequence: ImageSequence = new ImageSequence(1, 1, 0, "black", "bl_", ".jpg", new Vector2D(0,0), new Vector2D(1246, 796), false)
   
   /**
   Setup runs once when the sketch begins
   */
   override def setup(): Unit = {
      backgroundColor = new Color(0,0,0)
      overlayColor = new Color(0,0,0,30)
   }

   //some variables linked to the undulating ground plane
   var raise: Double = 0
   val raiseDivisor = 40
   var raising: Boolean = true

   /**
   Update gets called prior to draw each drawing cycle.
   It is the place where you should update animation properties
   prior to drawing.
   */
   override def update(): Unit = {

      //Camera.translate(new Vector3D(0,0,5))
      //Camera.rotateY(1)
      //Camera.rotateZ(.45)

      //Three examples of employing serial readings (from photoresistors)
      //first two examples are commented out.

      //adjusting renderer via three photoresistors
      /**
      val invertedReadings = Formulas.invertByteRestrictedIntValues(serialByteReadings)
      groundPlane.renderer.strokeColor = new Color(invertedReadings(0), invertedReadings(1), invertedReadings(2), 30)
      groundPlane.renderer.fillColor = new Color(serialByteReadings(0), serialByteReadings(1), serialByteReadings(2), 18)
      */

      //adjusting camera distance via a photoresistor
      /**
      scene.sprites(0).location.z = serialByteReadings(0) * (700 + scene.sprites(0).location.z/1000)
      */
      
      //changing renderer mode on the basis or sensor readings
      val least: Int = Formulas.getLeastValueIndex(serialByteReadings)
      //println("least: " + least)
      least match {
         case 0 => groundPlane.renderer.mode = Renderer.POINTS
         case 1 => groundPlane.renderer.mode = Renderer.LINES
         case 2 => groundPlane.renderer.mode = Renderer.FILLED
      }

      if (!paused) {

         //makes the scull image vary its transparencey (apparently flicker)
         //image, alpha, minBrightness, maxBrightness
         ImageAdjust.setTransparency(alphaBuff, Randomise.range(0,100), 5, 256)

         //raise and lower the height map
         if (raising) {
            if(raise<40) {
               for (i <- 0 until gridPlane.points.size) {
                  if (hMap.heightMap(i) > 2) {
                     gridPlane.points(i).y += ((((hMap.heightMap(i))+10))*100)/raiseDivisor
                  }
               }
               raise += 1
            } else {
               raising = false
            }
         } else {
            if(raise>0) {
               for (i <- 0 until gridPlane.points.size) {
                  if (hMap.heightMap(i) > 2) {
                     gridPlane.points(i).y -= ((((hMap.heightMap(i))+10))*100)/raiseDivisor
                  }
               }
               raise -= 1
            } else {
               raising = true
            }
         }

         scene.update()//keeps updating the sprite on the basis of the animator values
         
         //adjust the volume of sound 0 - the nearer the louder, the further way the softer
         val inversePercent = getPercentSoundDistance(groundPlane.location.z)
         //println("inversePercent: " + inversePercent)
         soundManager.setVolume(0, inversePercent)

      }
      
   }

   /**
   Here is where you draw to the screen
   */
   override def draw(g2D: Graphics2D): Unit = {
      //drawBackground(g2D)//to draw repeatedly
      drawBackgroundOnce(g2D)//to draw just once when the sketch first runs

      //draws the sprite on the basis of its renderer attributes
      scene.draw(g2D)

      g2D.drawImage(alphaBuff, null, width/2-230, height/2-320)

      //calls method below to draw central axes on the screen
      //drawAxes(g2D)
      drawOverlay(g2D)

   }
   /**
   calculates an inverse percent for simulating distance based sound
   */
   def getPercentSoundDistance(zVal: Double): Float = {
      val percent: Double = Formulas.percentage(zVal, silentDistanceZ)
      //println("zVal: " + zVal)
      //println("percent: " + percent)
      (100 - percent).toFloat
   }

   
    /**
   serialEventNotify is for discontinuous serial messages - like RFID codes.
   Receives messages sent from Sketch via interaction manager and serial port (in Main)
   */
   override def serialEventNotify(): Unit = {
      //uncomment this code when you want to read RFID
      /**
      println("new rfid reading: " + serialStringReading)
      serialStringReading match {
         case "1500D001B" => groundPlane.renderer.mode = Renderer.POINTS
         case "1500CFF3B" => groundPlane.renderer.mode = Renderer.LINES
         case "1500D0025" => groundPlane.renderer.mode = Renderer.FILLED
         case "1500CFEA8" => groundPlane.renderer.mode = Renderer.FILLED_STROKED
      }
      */
   }
   

}


