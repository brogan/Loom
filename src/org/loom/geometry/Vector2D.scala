/**
Vector2D represents a 2D coordinate or a trajectory.  You can see it as an x and a y coordinate or as speed, size, scaling factor, etc. - anything that has an x and a y component.
*/

package org.loom.geometry

import org.loom.utility._

class Vector2D(var x: Double, var y: Double) {

   override def toString(): String = "point x: " + x + "   y: " + y
   /**
   Translate the Vector2D by an x and y vector (Vector2D)
   @param trans the translation vector expressed as a Vector2D
   */
   def translate(trans: Vector2D): Unit = {
      val t: Vector2D = Transform2D.translate(this, trans)
      this.x = t.x
      this.y = t.y
   }
   /**
   Scale the Vector2D by an x and y scaling factor (Vector2D)
   @param scale the scaling factor expressed as a Vector2D
   */
   def scale(scale: Vector2D): Unit = {
      val s: Vector2D = Transform2D.scale(this, scale)
      this.x = s.x
      this.y = s.y
   }
   /**
   Rotate the Vector2D by an angle
   @param angle the amount to rotate
   */
   def rotate(angle: Double): Unit = {
      val r: Vector2D = Transform2D.rotate(this, angle)
      this.x = r.x
      this.y = r.y
   }
   /**
   Clone the Vector2D.
   @return independent copy of this Vector2D
   */
   override def clone(): Vector2D = {
      new Vector2D(x, y)
   }

}

object Vector2D {

   /**
   Tests if two Vector2Ds are equal.
   @param vA the first Vector2D
   @param vB the second Vector2D
   @return Boolean
   */
   def equals(vA: Vector2D, vB: Vector2D): Boolean = {
      if (vA.x == vB.x && vA.y == vB.y) true else false
   }
   /**
   Invert the values of input Vector2D.
   Positive values become negative. Negative values become positive.
   @param vector the Vector3D to be inverted
   @return inverted Vector2D
   */
   def invert(vector: Vector2D): Vector2D = {
      new Vector2D(-(vector.x), -(vector.y))
   }
   /**
   Get the difference between two Vecto2Ds.
   Subtract child vector from parent vector.
   @param child vector (Vector2D)
   @param parent vector (Vector2D)
   @return difference Vector2D
   */
   def difference(child: Vector2D, parent: Vector2D): Vector2D = {
      new Vector2D(parent.x - child.x, parent.y - child.y)
   }

}
