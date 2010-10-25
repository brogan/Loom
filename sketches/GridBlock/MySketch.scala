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
This sketch demos creation and animation of a gridBlock (see geometry/ShapeCreator).
Please Note.
MySketch inherits the following properties from Sketch:
backgroundColor, overlayColor, axesColor, axesStrokeWeight, paused, serialByteTeadings, serialStringReadings,
renderer.
MySketch inherits the following methods from Sketch:
setup, update, draw, drawBackground, drawBackgroundOnce, drawOverlay, drawAxes, serialEventNotify.
*/
class MySketch(width: Int, height: Int) extends Sketch (width, height) {

   //create a renderer
   //renderer = new Renderer(Renderer.FILLED_STROKED, .2f, new Color(255,255,255,10), new Color(120,10,3,3))
   renderer = new Renderer(Renderer.LINES, .05f, new Color(255,255,255,50), new Color(120,10,3,20))
   //renderer = new Renderer(Renderer.POINTS, .2f, new Color(255,255,255,50), new Color(120,10,3,20))
   /**
   var p1: Vector3D = new Vector3D(-1,0,0)
   var p2: Vector3D = new Vector3D(1,0,0)
   val line: Polygon3D = new Polygon3D(List(p1, p2))

   //create a 3D shape out of the polygons
   val shape3D: Shape3D = new Shape3D(List(p1, p2), List(line))
   */

//////////////////////////////////////////////
   val imageFileName: String = "mountain.jpg"
//////////////////////////////////////////////
   //the mountain image is used for the height map to displace the grid block
   val image: Image = ImageLoader.imageLoad(ProjectFilePath.getResourceFilePath(ProjectFilePath.IMAGE, imageFileName))
   val buff: BufferedImage = image.asInstanceOf[BufferedImage]

   //THE HEIGHT MAP NEEDS TO HAVE ONE MORE ROW AND COLUMN THAN THE GRID BLOCK
   //BECAUSE THERE ARE ONE MORE SET OF POINTS FOR EACH ROW AND EACH COLUMN
   //THINK ABOUT IT: A ROW OF FOUR COLUMNS ACTUALLY REQUIRES FIVE POINTS
   val hMap: ImageToHeightMap = new ImageToHeightMap(buff, 7, 7, 10)

   val shape3D: Shape3D = ShapeCreator.makeGridBlock(6,6,6)

   //set some initial parameters for the shape
   val loc: Vector3D = new Vector3D(0,0,2000)//initial location
   val size: Vector3D = new Vector3D(200, 200, 200)//initial scale
   val startRotation: Vector3D = new Vector3D(0, 0, 0)//initial rotation
   //rotOffset controls where the rotation point is set in the shape
   //so, for example, negative 1 on z brings all the points in the object
   //back by -1, which puts the rotation point on the tip of the crystal
   val rotOffset: Vector3D = new Vector3D(0, 0, 0)

   //set some animation parameters for the shape
   val scaleFactor: Vector3D = new Vector3D(1, 1, 1)
   val rotFactor: Vector3D = new Vector3D(.2,.3,.1)
   val speedFactor: Vector3D = new Vector3D(0, 0, 0)

   //create an animator based on the above parameters
   val animator: Animator3D = new Animator3D(true, scaleFactor, rotFactor, speedFactor)

   //create a sprite from the shape, initial parameters, animator and renderer
   val sprite3D: Sprite3D = new Sprite3D(shape3D,loc,size,startRotation,rotOffset,animator,renderer) 


   //create a notional view
   //parameters: screen width, screen height, view width, view height, border width, border height
   val view3D: View3D = new View3D(width, height, width, height, 0, 0)
   //create a scene
   val scene: Scene3D = new Scene3D()
   //set properties in Camera object: view3D, viewAngle and scene3D
   Camera.view3D = view3D
   Camera.viewAngle = 75
   Camera.scene = scene

   scene.addSprite(sprite3D)
 
   /**
   Setup runs once when the sketch begins
   */
   override def setup(): Unit = {
      backgroundColor = new Color(0,0,0)
      overlayColor = new Color(0,0,0, 15)
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

      if (!paused) {
         //raise and lower the height map
         //for a gridBlock (not a gridPlane)
         if (raising) {
            if(raise<40) {
               for (i <- 0 until shape3D.points.size) {
                  if (hMap.heightMap(i/hMap.rows) > 0) {
                     shape3D.points(i).y += ((((hMap.heightMap(i/hMap.rows))+10))*10)/raiseDivisor
                  } else {
                     shape3D.points(i).x += ((((hMap.heightMap(i/hMap.rows))+10))*10)/raiseDivisor
                  }
               }
               renderer.fillColor = new Color(Randomise.range(200,250), Randomise.range(150,200), Randomise.range(0,20), raise.toInt)
               raise += 1
            } else {
               raising = false
            }
         } else {
            if(raise>0) {
               for (i <- 0 until shape3D.points.size) {
                  if (hMap.heightMap(i/hMap.rows) > 0) {
                     shape3D.points(i).y -= ((((hMap.heightMap(i/hMap.rows))+10))*10)/raiseDivisor
                  } else {
                     shape3D.points(i).x -= ((((hMap.heightMap(i/hMap.rows))+10))*10)/raiseDivisor
                  }
               }
               renderer.fillColor = new Color(Randomise.range(200,250), Randomise.range(150,200), Randomise.range(0,20), raise.toInt)
               raise -= 1
            } else {
               raising = true
            }
         }

         scene.update()//keeps updating the sprite on the basis of the animator values
      }


   }

   /**
   Here is where you draw to the screen
   */
   override def draw(g2D: Graphics2D): Unit = {
      drawBackground(g2D)//to draw repeatedly
      //drawBackgroundOnce(g2D)//to draw just once when the sketch first runs
      scene.draw(g2D)
      drawOverlay(g2D)

   }







}




