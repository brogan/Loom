/**
Polygon3D represents a 3D polygon
It stores a list of references to relevant points in a Shape3D
*/

package org.loom.geometry

class Polygon3D(val points: List[Vector3D], val polyType: Int) {
   
   var sidesTotal: Int = 0
   if(polyType == PolygonType.Line_Polygon) {
       sidesTotal = points.length
   } else if (polyType == PolygonType.Spline_Polygon) {
       sidesTotal = points.length/4
   }
   println("sides total: " + sidesTotal)
   override def toString(): String = "Polygon3D sidesTotal: " + sidesTotal
   def print(): Unit = { println("\n" + this.toString()); for (point <- points) println(point) }
   /**
   Translate the Polygon3D by an x, y, z vector(Vector3D).  Goes through all the points in the polygon and translates them.
   @param trans the translation vector expressed as a Vector3D
   */
   def translate(trans: Vector3D): Unit = {
      for (point <- points)point.translate(trans)
   }
   /**
   Scale the Polygon3D by an x, y, z vector(Vector3D).  Goes through all the points in the polygon and scales them.
   @param scale the scale vector expressed as a Vector3D
   */
   def scale(scale: Vector3D): Unit = {
      for (point <- points)point.scale(scale)
   }
   /**
   Rotate the Polygon3D on the x axis by an angle.  Goes through all the points in the polygon and rotates them.
   @param angle the angle of rotation on x axis
   */
   def rotateX(angle: Double): Unit = {
      for (point <- points)point.rotateX(angle)
   }
   /**
   Rotate the Polygon3D on the y axis by an angle.  Goes through all the points in the polygon and rotates them.
   @param angle the angle of rotation on y axis
   */
   def rotateY(angle: Double): Unit = {
      for (point <- points)point.rotateY(angle)
   }
   /**
   Rotate the Polygon3D on the z axis by an angle.  Goes through all the points in the polygon and rotates them.
   @param angle the angle of rotation on z axis
   */
   def rotateZ(angle: Double): Unit = {
      for (point <- points)point.rotateZ(angle)
   }

   /**
   Get central point
   @return center (Vector3D)
   */
   def getCenter(): Vector3D = {
      var totX: Double = 0
      var totY: Double = 0
      var totZ: Double = 0
      for (point <- points) {
         totX += point.x
         totY += point.y
         totZ += point.z
      }
      totX = totX/points.length
      totY = totY/points.length
      totZ = totZ/points.length
      new Vector3D(totX, totY, totZ)
   }
   /**
   This clones the polygon in such a way that the new polygon shares the
   same Vector3D references (points) as the original polygon
   @return Polygon3D (shallow copy)
   */
   override def clone(): Polygon3D = {
      val copy: Array[Vector3D] = new Array[Vector3D](sidesTotal)
      var i: Int = 0
      for (point <- points) { copy(i) = point; i += 1 }
      new Polygon3D(copy.toList, polyType)
   }
   /**
   This creates a deep clone of the polygon, so that the polygon refers
   to a new set of points.
   @param newPoints the new set of points for the overall new Shape3D
   @param vertexOrder the array of indices in newPoints relevant to this polygon
   @return Polygon3D (independent copy)
   */
   def clone(newPoints: Array[Vector3D], vertexOrder: Array[Int]): Polygon3D = {
      val copyPoly: Array[Vector3D] = new Array[Vector3D](sidesTotal)
      for (i <- 0 until points.size) copyPoly(i) = newPoints(vertexOrder(i))
      new Polygon3D(copyPoly.toList, polyType)
   }

}



