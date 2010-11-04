/**
Sprite2D represents a 2D sprite with a location, size and speed.  A sprite contains a shape, which is composed of a set of polygons, each of which are themselves composed of a set of Vector2Ds.  A sprite gets moved around a screen, while the underlying shape always remains at 0,0 for rotation and scaling purposes.  The trick is to draw the shape wherever the sprite happens to be - so at the last second we add the x and y of the sprite to the shapes 0,0 oriented coordinates.
Parameters
SHAPE: the Shape2D to move, rotate, scale
LOCATION: the x,y coordinate of the sprite (Vector2D)
SIZE: the x,y dimensions of the sprite (Vector2D)
ROTATION: the angle of rotation
SPEED: the x,y movement vector (Vector2D)
ANIMATOR: takes a custom animator class which extends Animator2D
RENDERER: renderer for the sprite
*/

package org.loom.scene

import org.loom.geometry._
import org.loom.utility._

import java.awt.{Graphics2D,BasicStroke}
import java.awt.geom._
import java.awt.Polygon

class Sprite2D(val shape: Shape2D, var location: Vector2D, var size: Vector2D, var startRotation: Double, var rotOffset: Vector2D, var animator: Animator2D, var renderer: Renderer) extends Drawable {

   var pointWidth: Double = 2
   var pointHeight: Double = 2

   shape.translate(rotOffset)//move shape not sprite because establishing rotation point
   rotate(startRotation)//at beginning (or whenever necessary)
   scale(size)//at beginning (or whenever necessary)

   /**
   Translate the sprite position
   @param trans new position (Vector2D)
   */
   def translate(trans: Vector2D): Unit = {
      location = Transform2D.translate(location, trans)
   }

   /**
   Scale the sprite
   @param s scaling factor (Vector2D)
   */
   def scale(scale: Vector2D): Unit = {
      shape.scale(scale)
   }
   /**
   Rotate the sprite 
   @param angle angle increment
   */
   def rotate(angle: Double): Unit = {
      shape.rotate(angle)
   }
   /**
   Rotate the sprite around a parent
   Not implemented 
   @param angle angle increment
   */
   def rotateAroundParent(rot: Double, parent: Vector2D): Unit = {
      //shape.rotate(angle)
   }
   /**
   Clone the Sprite2D.  Produces an independent copy.
   @return cloned Sprite2D
   */
   override def clone(): Sprite2D = {
      var s: Shape2D = shape.clone()
      var loc: Vector2D = location.clone()
      var sz: Vector2D = size.clone()
      var ro: Vector2D = rotOffset.clone()

      s.translate(Vector2D.invert(ro))//to return it back to original centred position
      val invertedStartRotation: Double = -(startRotation)
      s.rotate(invertedStartRotation)
      s.scale(new Vector2D(1/sz.x, 1/sz.y))//to return it back to original scale

      var a: Animator2D = animator.clone()
      var r: Renderer = renderer.clone()
      new Sprite2D(s, loc, sz, startRotation, ro, a, r)
   }
   /**
   Check intersect with another sprite within a specific distance
   @param otherSprite the other sprite (Vector2D)
   @param dist collision distance
   @return Boolean
   */
   def checkIntersect(otherSprite: Sprite2D, dist: Double): Boolean = {
      val h: Double = Formulas.hypotenuse(location, otherSprite.location)
      if (h<=dist) true else false
   }
   /**
   toString - gets location, size, startRotation and rotOffset
   @return String representation of sprite2D properties
   */
   override def toString(): String = "Sprite2D location: (" + location.x + ", " + location.y + ")  size: (" + 
      size.x + ", " + size.y + ")  start rotation: " + startRotation + "  rotOffset: (" + rotOffset.x + ", " + rotOffset.y + ")"

   def update(): Unit = {
      animator.update(this)
   }
   /**
   Draw, draws a sprite
   Handles all sprite draw calls and passes them to specialised rendering methods depending on the rendering mode.
   @param g2D the Graphics2D context
   */
   def draw(g2D: Graphics2D): Unit = {

      renderer.mode match {
         case Renderer.POINTS => for (poly <- shape.polys) drawPoints(g2D, poly, Camera.view)
         case Renderer.LINES => for (poly <- shape.polys) drawLines(g2D, poly, Camera.view)
         case Renderer.FILLED => for (poly <- shape.polys) drawFilled(g2D, poly, Camera.view)
         case Renderer.FILLED_STROKED => for (poly <- shape.polys) drawFilledStroked(g2D, poly, Camera.view)
      }

   }

   def drawPoints(g2D: Graphics2D, pol: Polygon2D, view: View): Unit = {
       g2D.setColor(renderer.strokeColor)
       g2D.setStroke(new BasicStroke(renderer.strokeWidth))
       
       val polyCorrected: Polygon2D = coordinateCorrect(pol, view)
       
       val sX: Int = location.x.toInt
       val sY: Int = location.y.toInt
       for(point <- polyCorrected.points) {
          val e: Ellipse2D.Double = new Ellipse2D.Double(point.x.toInt + sX, point.y.toInt + sY, pointWidth,pointHeight)
          g2D.draw(e)
       }
   }


   def drawLines(g2D: Graphics2D, pol: Polygon2D, view: View): Unit = {
      
      val polyCorrected: Polygon2D = coordinateCorrect(pol, view)
	   
      val poly: Polygon = getPolygonFromPolygon2D(polyCorrected)
      if (pol.polyType == PolygonType.Line_Polygon) {
           g2D.setColor(renderer.strokeColor)
           g2D.setStroke(new BasicStroke(renderer.strokeWidth))
           g2D.draw(poly)
      } else {
          //iterate through each of the spline segments in the spline Polygon3d
          //println("poly sides total: " + poly2D.sidesTotal)
          val path: GeneralPath = new GeneralPath(Path2D.WIND_EVEN_ODD)
          path.moveTo(polyCorrected.points(0).x.toFloat, polyCorrected.points(0).y.toFloat)
          for (i <- 0 until pol.sidesTotal) {
    	      val c1X: Float = polyCorrected.points(1 + i*4).x.toFloat
    	      val c1Y: Float = polyCorrected.points(1 + i*4).y.toFloat
    	      val c2X: Float = polyCorrected.points(2 + i*4).x.toFloat
    	      val c2Y: Float = polyCorrected.points(2 + i*4).y.toFloat
    	      val a2X: Float = polyCorrected.points(3 + i*4).x.toFloat
    	      val a2Y: Float = polyCorrected.points(3 + i*4).y.toFloat
		      path.curveTo(c1X, c1Y, c2X, c2Y, a2X, a2Y);
          }
          g2D.setColor(renderer.strokeColor)
          g2D.setStroke(new BasicStroke(renderer.strokeWidth))
          g2D.draw(path)
       }

   }

   def drawFilled(g2D: Graphics2D, pol: Polygon2D, view: View): Unit = {
	   
	  val polyCorrected: Polygon2D = coordinateCorrect(pol, view)
	   
      val poly: Polygon = getPolygonFromPolygon2D(polyCorrected)
      if (pol.polyType == PolygonType.Line_Polygon) {
           g2D.setColor(renderer.fillColor)
           g2D.fill(poly)
      } else {
          //iterate through each of the spline segments in the spline Polygon3d
          //println("poly sides total: " + poly2D.sidesTotal)
          val path: GeneralPath = new GeneralPath(Path2D.WIND_EVEN_ODD)
          path.moveTo(polyCorrected.points(0).x.toFloat, polyCorrected.points(0).y.toFloat)
          for (i <- 0 until pol.sidesTotal) {
    	      val c1X: Float = polyCorrected.points(1 + i*4).x.toFloat
    	      val c1Y: Float = polyCorrected.points(1 + i*4).y.toFloat
    	      val c2X: Float = polyCorrected.points(2 + i*4).x.toFloat
    	      val c2Y: Float = polyCorrected.points(2 + i*4).y.toFloat
    	      val a2X: Float = polyCorrected.points(3 + i*4).x.toFloat
    	      val a2Y: Float = polyCorrected.points(3 + i*4).y.toFloat
		      path.curveTo(c1X, c1Y, c2X, c2Y, a2X, a2Y);
          }
          g2D.setColor(renderer.fillColor)
          g2D.fill(path)
       }

   }

   def drawFilledStroked(g2D: Graphics2D, pol: Polygon2D, view: View): Unit = {

      val polyCorrected: Polygon2D = coordinateCorrect(pol, view)
	   
      val poly: Polygon = getPolygonFromPolygon2D(polyCorrected)
      if (pol.polyType == PolygonType.Line_Polygon) {
           g2D.setColor(renderer.fillColor)
           g2D.fill(poly)
           g2D.setColor(renderer.strokeColor)
           g2D.setStroke(new BasicStroke(renderer.strokeWidth))
           g2D.draw(poly)
      } else {
          //iterate through each of the spline segments in the spline Polygon3d
          //println("poly sides total: " + poly2D.sidesTotal)
          val path: GeneralPath = new GeneralPath(Path2D.WIND_EVEN_ODD)
          path.moveTo(polyCorrected.points(0).x.toFloat, polyCorrected.points(0).y.toFloat)
          for (i <- 0 until pol.sidesTotal) {
    	      val c1X: Float = polyCorrected.points(1 + i*4).x.toFloat
    	      val c1Y: Float = polyCorrected.points(1 + i*4).y.toFloat
    	      val c2X: Float = polyCorrected.points(2 + i*4).x.toFloat
    	      val c2Y: Float = polyCorrected.points(2 + i*4).y.toFloat
    	      val a2X: Float = polyCorrected.points(3 + i*4).x.toFloat
    	      val a2Y: Float = polyCorrected.points(3 + i*4).y.toFloat
		      path.curveTo(c1X, c1Y, c2X, c2Y, a2X, a2Y);
          }
          g2D.setColor(renderer.fillColor)
          g2D.fill(path)
          g2D.setColor(renderer.strokeColor)
          g2D.setStroke(new BasicStroke(renderer.strokeWidth))
          g2D.draw(path)
       }

   }
   
   def coordinateCorrect(pol: Polygon2D, view: View): Polygon2D = {
	   val ccPoints: Array[Vector2D] = new Array[Vector2D](pol.points.length)
	   var count: Int = 0
	   for (point <- pol.points) {
    	  val coordinateCorrection: Vector2D = view.viewToScreenVertex(new Vector2D(point.x, point.y))
          ccPoints(count) = new Vector2D(coordinateCorrection.x, coordinateCorrection.y)
          count += 1
       }
	   new Polygon2D(ccPoints.toList, pol.polyType)
	   
   }

   def getPolygonFromPolygon2D(pol: Polygon2D): Polygon = {
      val tot: Int = pol.points.length
      val sX: Int = location.x.toInt
      val sY: Int = location.y.toInt
      val xPoints: Array[Int] = new Array[Int](tot)
      val yPoints: Array[Int] = new Array[Int](tot)
      for (i <- 0 until tot) {
          xPoints(i) = pol.points(i).x.toInt + sX
          yPoints(i) = pol.points(i).y.toInt + sY
      }
      new Polygon(xPoints, yPoints, tot)
   }

}
