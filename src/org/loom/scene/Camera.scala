/**
Camera, paramaters: scene the scene contains a set of 3D sprites, viewWidth the width of the drawing panel, viewAngle the desired view angle in degrees.
The camera stays still - the world moves inversely in relation to the camera.
*/

package org.loom.scene

import org.loom.geometry._

import org.loom.utility._

object Camera {

   var view: View = new View(720,720,720,720,0,0)//default, set properly in MySketch
   var viewAngle: Double = 75//set from MySketch
   var scene: Scene = null//set from MySketch

   var translateSpeed: Double = 100
   var rotateSpeed: Double = .3

   var viewDistance: Double = 0
   setViewDistance()

   def update(): Unit = {

   }
   /**
   Translate
   @param speed (Vector3D)
   */
   def translate(speed: Vector3D): Unit = {
      val invert: Vector3D = Vector3D.invert(speed)
      scene.translate(invert)
   }
   /**
   Rotate - calls rotateAroundParent
   @param rotation (Vector3D)
   */
   def rotate(rotation: Vector3D): Unit = {
      val invert: Vector3D = Vector3D.invert(rotation)
      scene.rotateAroundParent(invert, new Vector3D (0, 0, 0))
   }
   /**
   Rotate x - calls rotateXAroundParent
   @param angle (Double)
   */
   def rotateX(angle: Double): Unit = {
      val invert: Double = -(angle)
      scene.rotateXAroundParent(invert, new Vector3D (0, 0, 0))
   }
   /**
   Rotate y - calls rotateYAroundParent
   @param angle (Double)
   */
   def rotateY(angle: Double): Unit = {
      val invert: Double = -(angle)
      scene.rotateYAroundParent(invert, new Vector3D (0, 0, 0))
   }
   /**
   Rotate z - calls rotateZAroundParent
   @param angle (Double)
   */
   def rotateZ(angle: Double): Unit = {
      val invert: Double = -(angle)
      scene.rotateZAroundParent(invert, new Vector3D (0, 0, 0))
   }

   def setViewDistance(): Unit = {
      var angleRadians: Double = Formulas.degreesToRadians(viewAngle)
      viewDistance = (view.viewWidth/2)/math.tan(angleRadians/2)
   }

}
