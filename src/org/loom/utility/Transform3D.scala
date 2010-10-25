/**
Transform3D provides translation, scaling and rotation code for Vector3Ds
*/

package org.loom.utility

import org.loom.geometry._

object Transform3D {

   /**
   translate (move) a 3D position (Vector3D) via a translation vector (Vector3D)
   @param origPos the original position
   @param trans the translation vector
   */
   def translate(origPos: Vector3D, trans: Vector3D): Vector3D = {
      new Vector3D(origPos.x+trans.x, origPos.y+trans.y, origPos.z+trans.z)
   }
   /**
   scale a 3D point (Vector3D) via a scaling factor (Vector3D)
   @param origPos the 3D point
   @param scale the scaling factor
   */
   def scale(origPos: Vector3D, scale: Vector3D): Vector3D = {
      new Vector3D(origPos.x*scale.x, origPos.y*scale.y, origPos.z*scale.z)
   }

   //X AXIS ROTATION
   /**
   rotate a 3D point (Vector3D) via an angle on x axis
   @param origPos the 3D point
   @param angle the rotation angle on x axis
   */
   def rotateX(origPos: Vector3D, ang: Double): Vector3D = {
      val ratios: Vector2D = getCosSin(ang)
      val rotY: Double = ((origPos.y * ratios.x) - (origPos.z * ratios.y))
      val rotZ: Double = ((origPos.y * ratios.y) + (origPos.z * ratios.x))
      new Vector3D(origPos.x, rotY, rotZ)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on x axis
   in relation to the difference from a parent position (defined by spriteOffset)
   @param origPos the 3D point
   @param angle the rotation angle on x axis
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateX(origPos: Vector3D, ang: Double, spriteOffset: Vector3D): Vector3D = {
      val ratios: Vector2D = getCosSin(ang)
      val rotY: Double = (((origPos.y + spriteOffset.y) * ratios.x) - ((origPos.z + spriteOffset.z) * ratios.y))
      val rotZ: Double = (((origPos.y + spriteOffset.y) * ratios.y) + ((origPos.z + spriteOffset.z) * ratios.x))
      new Vector3D(origPos.x, rotY, rotZ)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on x axis with precalculated cos sin ratios
   @param origPos the 3D point
   @param ratios precalculated cos and sin ratios stored in Vector2D
   */
   def rotateX(origPos: Vector3D, ratios: Vector2D): Vector3D = {
      val rotY: Double = ((origPos.y * ratios.x) - (origPos.z * ratios.y))
      val rotZ: Double = ((origPos.y * ratios.y) + (origPos.z * ratios.x))
      new Vector3D(origPos.x, rotY, rotZ)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on x axis with precalculated cos sin ratios
   in relation to the difference from a parent position (defined by spriteOffset)
   @param origPos the 3D point
   @param ratios precalculated cos and sin ratios stored in Vector2D
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateX(origPos: Vector3D, ratios: Vector2D, spriteOffset: Vector3D): Vector3D = {
      val rotY: Double = (((origPos.y + spriteOffset.y) * ratios.x) - ((origPos.z + spriteOffset.z) * ratios.y))
      val rotZ: Double = (((origPos.y + spriteOffset.y) * ratios.y) + ((origPos.z + spriteOffset.z) * ratios.x))
      new Vector3D(origPos.x, rotY, rotZ)
   }


   //Y AXIS ROTATION
   /**
   rotate a 3D point (Vector3D) via an angle on y axis
   @param origPos the 3D point
   @param angle the rotation angle on y axis
   */
   def rotateY(origPos: Vector3D, ang: Double): Vector3D = {
      val ratios: Vector2D = getCosSin(ang)
      val rotX: Double = ((origPos.x * ratios.x) + (origPos.z * ratios.y))
      val rotZ: Double = ((origPos.x * -ratios.y) + (origPos.z * ratios.x))
      new Vector3D(rotX, origPos.y, rotZ)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on y axis
   in relation to the difference from a parent position (defined by spriteOffset)
   @param origPos the 3D point
   @param angle the rotation angle on y axis
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateY(origPos: Vector3D, ang: Double, spriteOffset: Vector3D): Vector3D = {
      val ratios: Vector2D = getCosSin(ang)
      val rotX: Double = (((origPos.x + spriteOffset.x) * ratios.x) + ((origPos.z + spriteOffset.z) * ratios.y))
      val rotZ: Double = (((origPos.x + spriteOffset.x) * -ratios.y) + ((origPos.z + spriteOffset.z) * ratios.x))
      new Vector3D(rotX, origPos.y, rotZ)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on y axis with precalculated cos sin ratios
   @param origPos the 3D point
   @param ratios precalculated cos and sin ratios stored in Vector2D
   */
   def rotateY(origPos: Vector3D, ratios: Vector2D): Vector3D = {
      val rotX: Double = ((origPos.x * ratios.x) + (origPos.z * ratios.y))
      val rotZ: Double = ((origPos.x * -ratios.y) + (origPos.z * ratios.x))
      new Vector3D(rotX, origPos.y, rotZ)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on y axis with precalculated cos sin ratios
   in relation to the difference from a parent position (defined by spriteOffset)
   @param origPos the 3D point
   @param ratios precalculated cos and sin ratios stored in Vector2D
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateY(origPos: Vector3D, ratios: Vector2D, spriteOffset: Vector3D): Vector3D = {
      val rotX: Double = (((origPos.x + spriteOffset.x) * ratios.x) + ((origPos.z + spriteOffset.z) * ratios.y))
      val rotZ: Double = (((origPos.x + spriteOffset.x) * -ratios.y) + ((origPos.z + spriteOffset.z) * ratios.x))
      new Vector3D(rotX, origPos.y, rotZ)
   }

   //Z AXIS ROTATION
   /**
   rotate a 3D point (Vector3D) via an angle on z axis
   @param origPos the 3D point
   @param angle the rotation angle on z axis
   */
   def rotateZ(origPos: Vector3D, ang: Double): Vector3D = {
      val ratios: Vector2D = getCosSin(ang)
      val rotX: Double = ((origPos.x * ratios.x) - (origPos.y * ratios.y))
      val rotY: Double = ((origPos.x * ratios.y) + (origPos.y * ratios.x))
      new Vector3D(rotX, rotY, origPos.z)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on z axis
   in relation to the difference from a parent position (defined by spriteOffset)
   @param origPos the 3D point
   @param angle the rotation angle on z axis
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateZ(origPos: Vector3D, ang: Double, spriteOffset: Vector3D): Vector3D = {
      val ratios: Vector2D = getCosSin(ang)
      val rotX: Double = (((origPos.x + spriteOffset.x) * ratios.x) - ((origPos.y + spriteOffset.y) * ratios.y))
      val rotY: Double = (((origPos.x + spriteOffset.x) * ratios.y) + ((origPos.y + spriteOffset.y) * ratios.x))
      new Vector3D(rotX, rotY, origPos.z)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on z axis with precalculated cos sin ratios
   @param origPos the 3D point
   @param ratios precalculated cos and sin ratios stored in Vector2D
   */
   def rotateZ(origPos: Vector3D, ratios: Vector2D): Vector3D = {
      val rotX: Double = ((origPos.x * ratios.x) - (origPos.y * ratios.y))
      val rotY: Double = ((origPos.x * ratios.y) + (origPos.y * ratios.x))
      new Vector3D(rotX, rotY, origPos.z)
   }
   /**
   rotate a 3D point (Vector3D) via an angle on z axis with precalculated cos sin ratios
   in relation to the difference from a parent position (defined by spriteOffset)
   @param origPos the 3D point
   @param ratios precalculated cos and sin ratios stored in Vector2D
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateZ(origPos: Vector3D, ratios: Vector2D, spriteOffset: Vector3D): Vector3D = {
      val rotX: Double = (((origPos.x + spriteOffset.x) * ratios.x) - ((origPos.y + spriteOffset.y) * ratios.y))
      val rotY: Double = (((origPos.x + spriteOffset.x) * ratios.y) + ((origPos.y + spriteOffset.y) * ratios.x))
      new Vector3D(rotX, rotY, origPos.z)
   }


   /**
   Calculates Cos and Sin ratios and stores in a Vector2D - x for Cos and y for Sin
   @param ang angle of rotation
   */
   def getCosSin(ang: Double): Vector2D = {
      val angle = Formulas.degreesToRadians(ang)
      val cosOfAngle: Double = math.cos(angle)
      val sinOfAngle: Double = math.sin(angle)
      new Vector2D(cosOfAngle, sinOfAngle)
   }

}
