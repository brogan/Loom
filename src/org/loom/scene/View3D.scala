/**
View3D the notional 3D view with 0,0,0 at the centre.
Translates from view coordinates to screen coordinates and vice versa.
Remember, the screen 0,0 (2D) is at the top left and y increases downwards.
The view is different.  It has 0,0,0 at the centre and y increases upwards.
*/

package org.loom.scene

import org.loom.geometry._

class View3D(val screenWidth: Int, val screenHeight: Int, val viewWidth: Int, val viewHeight: Int, val borderX: Int, val borderY: Int) {

   val viewCenterX:Int = viewWidth/2
   val viewCenterY:Int = viewHeight/2
   /**
   Converts a 3D view point to a drawing panel coordinate
   @param v input view point (Vector3D)
   @return Vector3D
   */
   def viewToScreenVertex(v: Vector3D): Vector3D = {
      val x: Double = borderX + viewCenterX + v.x
      val y: Double = borderY + viewCenterY - v.y
      new Vector3D(x, y, v.z)
   }
   /**
   Converts a drawing panel coordinate to a 3D view point
   @param v input drawing panel coordinate (Vector3D)
   @return Vector3D
   */
   def screenToViewVertex(v: Vector3D): Vector3D = {
      val x: Double = v.x - viewCenterX + borderX
      val y: Double = v.y + viewCenterY + borderY
      new Vector3D(x, y, v.z)
   }

}
