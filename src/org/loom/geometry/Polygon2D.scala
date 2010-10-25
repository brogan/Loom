/**
Polygon2D represents a 2D polygon composed of any number of sides.  The Polygon2D is represented as a set of Vector2D coordinates.
When creating Polygon2D objects, it is typically best to do so with the center at 0,0.  This makes things like rotating the polygon
around its center much easier.
*/

package org.loom.geometry

class Polygon2D(val points: List[Vector2D]) {

   val sidesTotal: Int = points.length
   override def toString(): String = "Polygon2D sidesTotal: " + sidesTotal
   def print(): Unit = { println("\n" + this.toString()); for (point <- points) println(point) }

   /**
   Translate the Polygon2D by an x and y vector(Vector2D).  Goes through all the points in the polygon and translates them.
   @param trans the translation vector expressed as a Vector2D
   */
   def translate(trans: Vector2D): Unit = {
      for (point <- points)point.translate(trans)
   }
   /**
   Scale the Polygon2D by an x and y scaling factor (Vector2D).  Goes through all the points in the polygon and scales them.
   @param scale the scaling factor expressed as a Vector2D
   */
   def scale(scale: Vector2D): Unit = {
      for (point <- points)point.scale(scale)
   }
   /**
   Rotate the Vector2D by an angle.
   @param angle the amount to rotate
   */
   def rotate(angle: Double): Unit = {
      for (point <- points)point.rotate(angle)
   }
   /**
   Clone the Polygon2D.
   @return independent copy of this Polygon2D
   */
   override def clone(): Polygon2D = {
      val copy: Array[Vector2D] = new Array[Vector2D](sidesTotal)
      var i: Int = 0
      for (point <- points) { copy(i) = point.clone(); i += 1 }
      new Polygon2D(copy.toList)
   }

}
