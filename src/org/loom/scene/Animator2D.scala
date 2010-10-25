/**
Animator2D
*/

package org.loom.scene

import org.loom.geometry._

class Animator2D(var animating: Boolean, var scale: Vector2D, var rotation: Double, var speed: Vector2D)  {

   /**
   Update sprite scale, rotation and translation
   */
   def update(sprite: Sprite2D) = {
      if (animating) {
         if (!Vector2D.equals(scale, new Vector2D(1,1))) sprite.scale(scale)
         if (rotation != 0) sprite.rotate(rotation)
         if (!Vector2D.equals(speed, new Vector2D(0,0))) sprite.translate(speed)
      }
   }
   /**
   Clone the Animator2D.  Produces an independent copy.
   @return Animator2D
   */
   override def clone(): Animator2D = {
      var sc: Vector2D = scale.clone()
      var sp: Vector2D = speed.clone()
      new Animator2D(animating, sc, rotation, sp)
   }
   /**
   toString - gets animating, scale, rotation and speed
   @return String representation of animator2D properties
   */
   override def toString(): String = "Animator3D animating: (" + animating + ", scale: " + scale.x + ",  rotation: " + 
      rotation + ", speed: " + speed.x + ", " + speed.y

}
