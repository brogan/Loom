/**
Transform2D provides translation, scaling and rotation code for Vector2Ds
*/

package org.loom.utility

import org.loom.geometry._

object Transform2D {

   /**
   translate (move) a 2D position (Vector2D) via a translation vector (Vector2D)
   @param origPos the original position
   @param trans the translation vector
   */
   def translate(origPos: Vector2D, trans: Vector2D): Vector2D = {
      new Vector2D(origPos.x+trans.x, origPos.y+trans.y)
   }
   /**
   scale a 2D point (Vector2D) via a scaling factor (Vector2D)
   @param origPos the 2D point
   @param scale the scaling factor
   */
   def scale(origPos: Vector2D, scale: Vector2D): Vector2D = {
      new Vector2D(origPos.x*scale.x, origPos.y*scale.y)
   }
   /**
   rotate a 2D point (Vector2D) via an angle
   @param origPos the 2D point
   @param angle the rotation angle
   */
   def rotate(origPos: Vector2D, ang: Double): Vector2D = {
      val angle = Formulas.degreesToRadians(ang)
      val cosOfAngle: Double = math.cos(angle)
      val sinOfAngle: Double = math.sin(angle)
      val rotX: Double = ((origPos.x * cosOfAngle) - (origPos.y * sinOfAngle))
      val rotY: Double = ((origPos.x * sinOfAngle) + (origPos.y * cosOfAngle))
      new Vector2D(rotX, rotY)
   }
}
