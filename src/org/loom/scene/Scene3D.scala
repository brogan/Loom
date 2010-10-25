/**
Scene3D
*/

package org.loom.scene

import org.loom.geometry._
import java.awt.Graphics2D
import scala.collection.mutable.ListBuffer

class Scene3D() {

   val sprites: ListBuffer[Sprite3D] = new ListBuffer[Sprite3D]()

   /**
   Adds a Sprite3D to the scene.
   @param sprite the Sprite3D to store
   */
   def addSprite(sprite: Sprite3D): Unit = {
      sprites += sprite
   }
   /**
   Adds a Sprite3D to the scene at a zIndex.
   @param sprite the Sprite3D to add
   @param zIndex the zIndex
   */
   def addSprite(sprite: Sprite3D, zIndex: Int): Unit = {
      sprites.insert(zIndex, sprite)
   }
   /**
   Removes a Sprite3D from the scene.
   @param sprite the Sprite3D to remove
   */
   def removeSprite(sprite: Sprite3D): Unit = {
      sprites -= sprite
   }
   /**
   Removes a Sprite3D from the scene.
   @param the zIndex of the sprite to remove
   */
   def removeSprite(zIndex: Int): Unit = {
      sprites.remove(zIndex)
   }
   /**
   Gets Sprite3D index in scene.
   @param sprite the Sprite3D to get index
   @return index (Int)
   */
   def getIndex(sprite: Sprite3D): Int = {
      var index: Int = -1
      for (i <- 0 until sprites.size) {
         if (sprites(i) == sprite) {
           index = i
         }
      }
      index
   }
   /**
   Change a Sprite3D zIndex in the scene
   @param sprite the Sprite3D to change index
   @param zIndex the new zIndex of the sprite
   */
   def changeZIndex(sprite: Sprite3D, zIndex: Int): Unit = {
      var index: Int = getIndex(sprite)
      addSprite(sprite, zIndex)
      removeSprite(index)
   }
   /**
   Gets the number of sprites in the scene.
   @return the number of sprites in the scene
   */
   def getSize(): Int = {
      sprites.size
   }
   /**
   Draw, draws a Scene3D
   Calls draw on all sprites in the scene
   @param g2D the Graphics2D context
   */
   def draw(g2D: Graphics2D): Unit = {
      for (sprite <- sprites) sprite.draw(g2D)
   }
   /**
   Update
   Calls update on all sprite3Ds in the scene
   */
   def update(): Unit = {
      for (sprite <- sprites) sprite.update()
   }
   /**
   Translate all the Sprite3Ds in the scene
   @param speed the translation vector (Vector3D)
   */
   def translate(speed: Vector3D): Unit = {
      for (sprite <- sprites) sprite.translate(speed)
   }
   /**
   Scale all the Sprite3Ds in the scene
   @param scale the scale vector (Vector3D)
   */
   def scale(scale: Vector3D): Unit = {
      for (sprite <- sprites) sprite.scale(scale)
   }
   /**
   Rotate all the Sprite3Ds in the scene on the x axis.
   @param angle the angle of rotation on x axis.
   */
   def rotateX(angle: Double): Unit = {
      for (sprite <- sprites) sprite.rotateX(angle)
   }
   /**
   Rotate all the Sprite3Ds in the scene on the y axis.
   @param angle the angle of rotation on y axis.
   */
   def rotateY(angle: Double): Unit = {
      for (sprite <- sprites) sprite.rotateY(angle)
   }
   /**
   Rotate all the Sprite3Ds in the scene on the z axis.
   @param angle the angle of rotation on z axis.
   */
   def rotateZ(angle: Double): Unit = {
      for (sprite <- sprites) sprite.rotateZ(angle)
   }
   /**
   */
   def rotateAroundParent(rotation: Vector3D, parent: Vector3D): Unit = {
      for (sprite <- sprites) sprite.rotateAroundParent(rotation, parent)
   }
   /**
   Rotate all the Sprite3Ds in the scene on the x axis with offset.
   Used particularly when rotating around camera.
   @param angle the angle of rotation on y axis.
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateXAroundParent(angle: Double, parent: Vector3D): Unit = {
      for (sprite <- sprites) sprite.rotateXAroundParent(angle, parent)
   }
   /**
   Rotate all the Sprite3Ds in the scene on the y axis with offset.
   Used particularly when rotating around camera.
   @param angle the angle of rotation on y axis.
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateYAroundParent(angle: Double, parent: Vector3D): Unit = {
      for (sprite <- sprites) sprite.rotateYAroundParent(angle, parent)
   }
   /**
   Rotate all the Sprite3Ds in the scene on the z axis with an offset.
   Used particularly when rotating around camera.
   @param angle the angle of rotation on z axis.
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateZAroundParent(angle: Double, parent: Vector3D): Unit = {
      for (sprite <- sprites) sprite.rotateZAroundParent(angle, parent)
   }

}
