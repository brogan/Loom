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

class Sprite2D(val shape: Shape2D, var location: Vector2D, var size: Vector2D, var startRotation: Double, var rotOffset: Vector2D, var animator: Animator2D, var renderer: Renderer) {

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
         case Renderer.POINTS => for (poly <- shape.polys) drawPoints(g2D, poly)
         case Renderer.LINES => for (poly <- shape.polys) drawLines(g2D, poly)
         case Renderer.FILLED => for (poly <- shape.polys) drawFilled(g2D, poly)
         case Renderer.FILLED_STROKED => for (poly <- shape.polys) drawFilledStroked(g2D, poly)
      }

   }

   def drawPoints(g2D: Graphics2D, pol: Polygon2D): Unit = {
      g2D.setColor(renderer.strokeColor)
      g2D.setStroke(new BasicStroke(renderer.strokeWidth))
      val sX: Int = location.x.toInt
      val sY: Int = location.y.toInt
      for(point <- pol.points) {
         val e: Ellipse2D.Double = new Ellipse2D.Double(point.x.toInt + sX, point.y.toInt + sY, pointWidth,pointHeight)
         g2D.draw(e)
      }
   }


   def drawLines(g2D: Graphics2D, pol: Polygon2D): Unit = {

      val poly: Polygon = getPolygonFromPolygon2D(pol)
      g2D.setColor(renderer.strokeColor)
      g2D.draw(poly)

   }

   def drawFilled(g2D: Graphics2D, pol: Polygon2D): Unit = {

      val poly: Polygon = getPolygonFromPolygon2D(pol)
      g2D.setColor(renderer.fillColor)
      g2D.fill(poly)

   }

   def drawFilledStroked(g2D: Graphics2D, pol: Polygon2D): Unit = {

      val poly: Polygon = getPolygonFromPolygon2D(pol)
      g2D.setColor(renderer.fillColor)
      g2D.fill(poly)
      g2D.setColor(renderer.strokeColor)
      g2D.draw(poly)

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
