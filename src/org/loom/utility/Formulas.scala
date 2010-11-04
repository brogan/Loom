/**
Formulas - some useful formulas
*/

package org.loom.utility

import org.loom.geometry._

object Formulas {

   /**
   Converts radians to degrees.
   @param radians
   @return degrees
   */
   def radiansToDegrees(radians: Double): Double = radians * (180/math.Pi)
   /**
   Converts degrees to radians.
   @param radians
   @return degrees
   */
   def degreesToRadians(degrees: Double): Double = degrees * (math.Pi/180)
   /**
   Calculates the distance between two 2D points
   @param startX the first point x coordinate
   @param startY the first point y coordinate
   @param destX the second point x coordinate
   @param destY the second point y coordinate
   @return the distance between the two points
   */
   def hypotenuse(startX: Double, startY: Double, destX: Double, destY: Double): Double = {
      val diffX: Double = math.abs(destX-startX)
      val diffY: Double = math.abs(destY-startY)
      math.sqrt((diffX*diffX)+(diffY*diffY))
   }
   /**
   Calculates the distance between two 2D points
   @param start the position of the first point expressed as Vector2D
   @param dest the position of the second point expressed as Vector2D
   @return the distance between the two points
   */
   def hypotenuse(start: Vector2D, dest: Vector2D): Double = {
      val diffX: Double = math.abs(dest.x-start.x)
      val diffY: Double = math.abs(dest.y-start.y)
      math.sqrt((diffX*diffX)+(diffY*diffY))
   }
   /**
   Calculates the distance between two 3D points.
   NEEDS TESTING.
   @param start the position of the first point expressed as Vector3D
   @param dest the position of the second point expressed as Vector3D
   @return the distance between the two points
   */
   def hypotenuse(start: Vector3D, dest: Vector3D): Double = {
      val diffX: Double = math.abs(dest.x-start.x)
      val diffY: Double = math.abs(dest.y-start.y)
      val diffZ: Double = math.abs(dest.z-start.z)
      math.sqrt((diffX*diffX)+(diffY*diffY)+(diffZ*diffZ))
   }
   /**
   Calculates oposite side when hypotenuse and angle are known
   @param hypo the length of the hypotenuse
   @param angle the angle
   @return the length of the opposite side
   */
   def opposite(hypo: Double, angle: Double): Double = {
      math.sin(angle.toRadians) * hypo
   }
   /**
   Calculates adjacent side when hypotenuse and angle are known
   @param hypo the length of the hypotenuse
   @param angle the angle
   @return the length of the opposite side
   */
   def adjacent(hypo: Double, angle: Double): Double = {
      math.cos(angle.toRadians) * hypo
   }
   /**
   Calculates a percentage based on a score and a total
   @param score 
   @param total
   @return the percentage the score is of the total
   */
   def percentage(score: Double, total: Double): Double = (score/total) * 100
   /**
   Scales a Vector3D on the basis of a specified view distance.
   @param point the position of the point expressed as Vector3D
   @param viewDist the view distance from the point
   @return the perspective scaled point for rendering on a 2D surface
   */
   def getPerspective(point: Vector3D, viewDist: Double): Vector3D = {
      val p: Double = viewDist/(point.z + viewDist)
      new Vector3D(point.x * p, point.y * p, 0)
   }
   /**
   Scales a translation Vector3D (speed, etc.) on the basis of a specified view distance.
   Warning: Deprecated (may no longer be necessary)
   @param point the position of the point expressed as Vector3D
   @param viewDist the view distance from the point
   @return the scaled translation (Vector3D)
   */
   def getZScaledTranslation(point: Vector3D, viewDist: Double): Vector3D = {
      val p: Double = viewDist/(point.z + viewDist)
      new Vector3D(point.x * p, point.y * p, point.z)
   }
   /**
   Converts a signed Byte value to an Int.
   Signed Bytes go from 0 to 127 and then from -128 (128) to -1 (255).
   We want to get an unsigned Byte value between 0 and 255 which is then
   return as an Int.
   @param b the signed Byte value
   */
   def signedByteToInt(b: Byte): Int = b & 0xFF

   /**
   Inverts an Int that is restricted to Byte values (0-255).
   Useful for creating an inverse relation between sensor readings
   and program parameters.
   @param b the signed Byte value to invert
   */
   def invertByteRestrictedIntValue(b: Int): Int = math.abs(b-255)
   /**
   Inverts an array of Ints tha are restricted to Byte values (0-255).
   Useful for creating an inverse relation between sensor readings
   and program parameters.
   @param b the signed Byte value to invert
   */
   def invertByteRestrictedIntValues(bA: Array[Int]): Array[Int] = {
      val aA: Array[Int] = new Array[Int](bA.length)
      for(i <- 0 until bA.length) aA(i) = invertByteRestrictedIntValue(bA(i))
      aA
   }
   /**
   Gets the least value in an Int array
   and returns the index.  Least must be less
   than 900000 as currently implemented.
   */
   def getLeastValueIndex(myArray: Array[Int]): Int = {
      var least: Int = 900000
      var index: Int = -1
      var count: Int = 0
      for (item <- myArray) {
         if (item < least) { least = item; index = count }
         count += 1
      }
      index
   } 

   /**
   Gets the greatest value in an Int array
   and returns the index.  Greatest must be greater
   than -900000 as currently implemented.
   */
   def getGreatestValueIndex(myArray: Array[Int]): Int = {
      var greatest: Int = -900000
      var index: Int = -1
      var count: Int = 0
      for (item <- myArray) {
         if (item > greatest) { greatest = item; index = count }
         count += 1
      }
      index
   }
   /**
    * Gets average of a list of Vector2D values
    */
   def average(points: List[Vector2D]): Vector2D = {
	   val p: Vector2D = new Vector2D(0,0)
	   for (point <- points) {
	  	   p.x += point.x
	  	   p.y += point.y
	   }
	   p.x = p.x/points.length
	   p.y = p.y/points.length
	   p
   }
      /**
    * Gets average of a list of Vector3D values
    */
   def average(points: List[Vector3D]): Vector3D = {
	   val p: Vector3D = new Vector3D(0,0,0)
	   for (point <- points) {
	  	   p.x += point.x
	  	   p.y += point.y
	  	   p.z += point.z
	   }
	   p.x = p.x/points.length
	   p.y = p.y/points.length
	   p.z = p.z/points.length
	   p
   }

}

