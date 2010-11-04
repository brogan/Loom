/**
PolygonCreator provides convenience methods for creating Polygon2Ds and Shape3Ds.
*/

package org.loom.geometry

import java.awt.geom._
import java.awt.Polygon

import org.loom.utility._

object PolygonCreator {

   /**
   Make a Polygon2D triangle of a specified width and height.
   @param width
   @param height
   @return Polygon2D
   */
   def makePolygon2DTriangle(width: Double, height: Double): Polygon2D = {
      makePolygon2D(3, width, height)
   }
   /**
   Make a Polygon2D five pronged star of a specified diameter.
   @param width
   @param height
   @return Polygon2D
   */
   def makePolygon2DStar(diameter: Double): Polygon2D = {
      makePolygon2DStar(10, diameter, .5, true, 1)
   }

   /**
   Make a Polygon2D with a specified number of sides and a width and height.
   @param numberOfSides
   @param width
   @param height
   @return Polygon2D
   */
   def makePolygon2D(numberOfSides: Int, width: Double, height: Double): Polygon2D = {
      
      val points: Array[Vector2D] = new Array[Vector2D](numberOfSides)

      val w: Double = width/2
      val h: Double = height/2
      val angInc: Double = 360.0/numberOfSides
      //println("angInc: " + angInc)
      points(0) = new Vector2D(0,-h)
      //println("point 0: " + points(0))
      for(i <- 1 until numberOfSides) {
         points(i) = Transform2D.rotate(points(i-1), angInc)
         //println("point " + i + ": " + points(i))
      }
      new Polygon2D(points.toList, PolygonType.Line_Polygon)

   }
   /**
   Make a Polygon2DStar - rotates an inner and outer circle of points to create star.
   @param numberOfSides (must be even)
   @param diameter
   @param proportion the proportion of the inner points in the star to the outer points - normally a fraction, say .5
   @param positiveSynch alignment of inner points to outer points - normally true for positive
   @param synchMultiplier how much to modify alignment of inner points (1 leaves alignment as standard)
   @return Polygon2D
   */
   def makePolygon2DStar(numberOfSides: Int, diameter: Double, proportion: Double, positiveSynch: Boolean, synchMultiplier: Double): Polygon2D = {
      
      val combined: Array[Vector2D] = new Array[Vector2D](numberOfSides)

      var prop:Double = 1/proportion

      var r: Double = diameter/2
      val angInc: Double = 360.0/numberOfSides
      //println("angInc: " + angInc)
      combined(0) = new Vector2D(0,-r)
      for(i <- 1 until numberOfSides) {
         if (i % 2 == 0) {
            combined(i) = Transform2D.rotate(combined(i-2), 2*angInc)
         }  
      }

      r = r/prop
      combined(1) = new Vector2D(0,-r)
      if (positiveSynch) {
         combined(1) = Transform2D.rotate(combined(1), angInc*synchMultiplier)
      } else {
         combined(1) = Transform2D.rotate(combined(1), -angInc*synchMultiplier)
      }

      for (i <- 2 until numberOfSides) {
         if (i % 2 != 0) {
            combined(i) = Transform2D.rotate(combined(i-2), 2*angInc)
         }
      }

      new Polygon2D(combined.toList, PolygonType.Line_Polygon)

   }
   /**
   Converts a Polygon2D to a Java Polygon for drawing purposes
   @param pol the Polygon2D to convert
   @return Polygon
   */
   def convertPolygon2DToPolygon(pol: Polygon2D): Polygon = {
      val tot: Int = pol.points.length
      val xPoints: Array[Int] = new Array[Int](tot)
      val yPoints: Array[Int] = new Array[Int](tot)
      for (i <- 0 until tot) {
          xPoints(i) = pol.points(i).x.toInt
          yPoints(i) = pol.points(i).y.toInt
      }
      new Polygon(xPoints, yPoints, tot)
   }
   /**
   Converts a Polygon3D to a Polygon2D for drawing purposes.
   Called from Sprite3D when drawing Shape3Ds
   @param pol the Polygon3D
   @return Polygon2D
   */
   def convertPolygon3DToPolygon2D(pol: Polygon3D): Polygon2D = {
      val a: Array[Vector2D] = new Array[Vector2D](pol.points.length)
      var count: Int = 0
      for(point <- pol.points) {
         a(count) = new Vector2D(point.x, point.y)
         count += 1
      }
      new Polygon2D(a.toList, pol.polyType)
   }
  

}
