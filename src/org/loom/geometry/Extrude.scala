/**
Extrude - used by ShapeCreator to create multiple extrudes
*/

package org.loom.geometry

class Extrude(val translation: Vector3D, val scale: Vector3D, val rotation: Vector3D)

object Extrude {

   val ROTATE_TOP: Int = 0
   val ROTATE_CENTER: Int = 1
   val ROTATE_BOTTOM: Int = 2

}
