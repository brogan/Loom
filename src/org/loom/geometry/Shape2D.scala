/**
Shape2D represents a 2D shape composed of multiple Polygon2Ds.
Each Polygon2D hold its own set of Vector2Ds.
*/

package org.loom.geometry

class Shape2D(val polys: List[Polygon2D]) {

   val polysTotal: Int = polys.length
   override def toString(): String = "Shape2D polysTotal: " + polysTotal
   def print(): Unit = { println("\n" + this.toString()); for (poly <- polys) println(poly) }


   /**
   Translate the Shape2D by an x and y (Vector2D).  Goes through all the polygons in the shape and translates them.
   @param trans the translation vector expressed as a Vector2D
   */
   def translate(trans: Vector2D): Unit = {
      for (poly <- polys)poly.translate(trans)
   }

   /**
   Scale the Shape2D by an x and y scaling factor (Vector2D).  Goes through all the polygons in the shape and scales them.
   @param scale the scaling factor expressed as a Vector2D
   */
   def scale(scale: Vector2D): Unit = {
      for (poly <- polys)poly.scale(scale)
   }

   /**
   Rotate the Shape2D by an angle.  Goes through all the polygons in the shape and rotates them.
   @param rotation the rotation angle
   */
   def rotate(angle: Double): Unit = {
      for (poly <- polys)poly.rotate(angle)
   }
   /**
   This creates a deep clone of the Shape2D.
   @return Shape2D (independent copy)
   */
   override def clone(): Shape2D = {
      val copy: Array[Polygon2D] = new Array[Polygon2D](polysTotal)
      var i: Int = 0
      for (poly <- polys) { copy(i) = poly.clone(); i += 1 }
      new Shape2D(copy.toList)
   }

}
