/**
Vector3D represents a 3D coordinate or a trajectory
*/

package org.loom.geometry

import org.loom.utility._

class Vector3D(var x: Double, var y: Double, var z: Double) {

   var scaleX: Double = 1
   var scaleY: Double = 1
   var scaleZ: Double = 1
   override def toString(): String = "point x: " + x + "   y: " + y + "   z: " + z
   /**
   Translate the Vector3D by an x, y, z vector (Vector3D)
   This involves adding the translation vector to the current x, y, z values.
   @param trans the translation vector expressed as a Vector3D
   */
   def translate(trans: Vector3D): Unit = {
      val t: Vector3D = Transform3D.translate(this, trans)
      this.x = t.x
      this.y = t.y
      this.z = t.z
   }
   /**
   Scale the Vector3D by an x, y, z vector (Vector3D).
   This involves multiplying the current x, y, z values by the scale vector.
   @param scale the scale factor expressed as a Vector3D
   */
   def scale(scale: Vector3D): Unit = {
      val s: Vector3D = Transform3D.scale(this, scale)
      this.x = s.x
      this.y = s.y
      this.z = s.z
   }

   /**
   Rotate vector on x axis.
   @param angle the angle of x axis rotation
   */
   def rotateX(angle: Double): Unit = {
      val r: Vector3D = Transform3D.rotateX(this, angle)
      this.x = r.x
      this.y = r.y
      this.z = r.z
   }
   /**
   Rotate vector on y axis.
   @param angle the angle of y axis rotation
   */
   def rotateY(angle: Double): Unit = {
      val r: Vector3D = Transform3D.rotateY(this, angle)
      this.x = r.x
      this.y = r.y
      this.z = r.z
   }
   /**
   Rotate vector on z axis.
   @param angle the angle of z axis rotation
   */
   def rotateZ(angle: Double): Unit = {
      val r: Vector3D = Transform3D.rotateZ(this, angle)
      this.x = r.x
      this.y = r.y
      this.z = r.z
   }
   /**
   Clone the Vector3D.
   @return independent copy of this Vector3D
   */
   override def clone(): Vector3D = {
      new Vector3D(x, y, z)
   }

}

object Vector3D {

   /**
   Tests if two Vector3Ds are equal.
   @param vA the first Vector3D
   @param vB the second Vector3D
   @return Boolean
   */
   def equals(vA: Vector3D, vB: Vector3D): Boolean = {
      if (vA.x == vB.x && vA.y == vB.y && vA.z == vB.z) true else false
   }
   /**
   Invert the values of input Vector3D.
   Positive values become negative. Negative values become positive.
   @param vector the Vector3D to be inverted
   @return inverted Vector3D
   */
   def invert(vector: Vector3D): Vector3D = {
      new Vector3D(-(vector.x), -(vector.y), -(vector.z))
   }
   /**
   Get the difference between two Vector3Ds.
   Subtract child vector from parent vector.
   @param child vector (Vector3D)
   @param parent vector (Vector3D)
   @return difference Vector3D
   */
   def difference(child: Vector3D, parent: Vector3D): Vector3D = {
      new Vector3D(parent.x - child.x, parent.y - child.y, parent.z - child.z)
   }

}

