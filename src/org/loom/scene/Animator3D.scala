/**
Animator3D
*/

package org.loom.scene

import org.loom.geometry._

class Animator3D(var animating: Boolean, var scale: Vector3D, var rotation: Vector3D, var speed: Vector3D) {

   /**
   Update sprite scale, rotation and translation
   */
   def update(sprite: Sprite3D) = {
      if (animating) {
         if (!Vector3D.equals(scale, new Vector3D(1,1,1))) sprite.scale(scale)
         if (!Vector3D.equals(rotation, new Vector3D(0,0,0))) sprite.rotate(rotation)
         if (!Vector3D.equals(speed, new Vector3D(0,0,0))) sprite.translate(speed)
      }
   }
   /**
   Clone the Animator3D.  Produces an independent copy.
   @return Animator3D
   */
   override def clone(): Animator3D = {
      var sc: Vector3D = scale.clone()
      var rot: Vector3D = rotation.clone()
      var sp: Vector3D = speed.clone()
      new Animator3D(animating, sc, rot, sp)
   }
   /**
   toString - gets animating, scale, rotation and speed
   @return String representation of animator3D properties
   */
   override def toString(): String = "Animator3D animating: (" + animating + ", scale: " + scale.x + ", " + scale.y + ", " + scale.z + ",  rotation: " + 
      rotation.x + ", " + rotation.y + ", " + rotation.z + ", speed: " + speed.x + ", " + speed.y + ", " + speed.z

}
