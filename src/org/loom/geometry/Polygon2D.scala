/**
Polygon2D represents a 2D polygon composed of any number of sides.  The Polygon2D is represented as a set of Vector2D coordinates.
When creating Polygon2D objects, it is typically best to do so with the center at 0,0.  This makes things like rotating the polygon
around its center much easier.
*/

package org.loom.geometry

import org.loom.utility.Formulas

class Polygon2D(val points: List[Vector2D], val polyType: Int) {

   var sidesTotal: Int = 0
   if(polyType == PolygonType.Line_Polygon) {
       sidesTotal = points.length
   } else if (polyType == PolygonType.Spline_Polygon) {
       sidesTotal = points.length/4
   }
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
      new Polygon2D(copy.toList, polyType)
   }
   /**
    * Return a subdivision surface version of this polygon
    */
   def subdivide():List[Polygon2D] = {
	   val middle: Vector2D = Formulas.average(points)
	   val newPoints: Array[Vector2D] = new Array[Vector2D]((points.length * 2) + 1)
	   newPoints(points.length * 2) = middle
	   val newPolys: Array[Polygon2D] = new Array[Polygon2D](points.length)
	   for (i <- 0 until 2 * points.length) {
	  	   if (i % 2 == 0) {
	  	  	   newPoints(i) = points(i/2).clone
	  	   } else {
	  	  	   newPoints(i) = Formulas.average(List(points(i/2), points((i/2)+1)))
	  	   }
	   }
	   newPolys.toList
   }

}
