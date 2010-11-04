/**
Scene3D
*/

package org.loom.scene

import org.loom.geometry._
import java.awt.Graphics2D
import scala.collection.mutable.ListBuffer

class Scene() {

   val sprites: ListBuffer[Drawable] = new ListBuffer[Drawable]()

   /**
   Adds a Drawable to the scene.
   @param sprite the Drawable to store
   */
   def addSprite(sprite: Drawable): Unit = {
      sprites += sprite
   }
   /**
   Adds a Drawable to the scene at a zIndex.
   @param sprite the Drawable to add
   @param zIndex the zIndex
   */
   def addSprite(sprite: Drawable, zIndex: Int): Unit = {
      sprites.insert(zIndex, sprite)
   }
   /**
   Removes a Drawable from the scene.
   @param sprite the Drawable to remove
   */
   def removeSprite(sprite: Drawable): Unit = {
      sprites -= sprite
   }
   /**
   Removes a Drawable from the scene.
   @param the zIndex of the sprite to remove
   */
   def removeSprite(zIndex: Int): Unit = {
      sprites.remove(zIndex)
   }
   /**
   Gets Drawable index in scene.
   @param sprite the Drawable to get index
   @return index (Int)
   */
   def getIndex(sprite: Drawable): Int = {
      var index: Int = -1
      for (i <- 0 until sprites.size) {
         if (sprites(i) == sprite) {
           index = i
         }
      }
      index
   }
   /**
   Change a Drawable zIndex in the scene
   @param sprite the Drawable to change index
   @param zIndex the new zIndex of the sprite
   */
   def changeZIndex(sprite: Drawable, zIndex: Int): Unit = {
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
   Draw, draws a Scene
   Calls draw on all sprites in the scene
   @param g2D the Graphics2D context
   */
   def draw(g2D: Graphics2D): Unit = {
      for (sprite <- sprites) sprite.draw(g2D)
   }
   /**
   Update
   Calls update on all Drawables in the scene
   */
   def update(): Unit = {
      for (sprite <- sprites) sprite.update()
   }
   
      /**
   Translate all the Sprite2D Drawables in the scene
   @param speed the translation vector (Vector2D)
   */
   def translate(speed: Vector2D): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite2D]).translate(speed)
   }
      /**
   Scale all the Sprite2D Drawables in the scene
   @param scale the scale vector (Vector2D)
   */
   def scale(scale: Vector2D): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite2D]).scale(scale)
   }
   /**
   Rotate all the Sprite2D Drawables in the scene
   @param angle the angle of rotation on x axis.
   */
   def rotate(angle: Double): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite2D]).rotate(angle)
   }
   /**
   * Rotate all the Sprite2D Drawables in the scene around a parent
   * Not implemented
   */
   def rotateAroundParent(rotation: Vector2D, parent: Vector3D): Unit = {
      //for (sprite <- sprites) sprite.rotateAroundParent(rotation, parent)
   }
   
   
   /**
   Translate all the Sprite3D Drawables in the scene
   @param speed the translation vector (Vector3D)
   */
   def translate(speed: Vector3D): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).translate(speed)
   }
   /**
   Scale all the Sprite3D Drawables in the scene
   @param scale the scale vector (Vector3D)
   */
   def scale(scale: Vector3D): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).scale(scale)
   }
   /**
   Rotate all the Sprite3D Drawables in the scene on the x axis.
   @param angle the angle of rotation on x axis.
   */
   def rotateX(angle: Double): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).rotateX(angle)
   }
   /**
   Rotate all the Sprite3D Drawables in the scene on the y axis.
   @param angle the angle of rotation on y axis.
   */
   def rotateY(angle: Double): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).rotateY(angle)
   }
   /**
   Rotate all the Sprite3D Drawables in the scene on the z axis.
   @param angle the angle of rotation on z axis.
   */
   def rotateZ(angle: Double): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).rotateZ(angle)
   }
   /**
    * rotate al the Sprite3D Drawables around a parent
   */
   def rotateAroundParent(rotation: Vector3D, parent: Vector3D): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).rotateAroundParent(rotation, parent)
   }
   /**
   Rotate all the Sprite3D Drawables in the scene on the x axis with offset.
   Used particularly when rotating around camera.
   @param angle the angle of rotation on y axis.
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateXAroundParent(angle: Double, parent: Vector3D): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).rotateXAroundParent(angle, parent)
   }
   /**
   Rotate all the Sprite3D Drawables in the scene on the y axis with offset.
   Used particularly when rotating around camera.
   @param angle the angle of rotation on y axis.
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateYAroundParent(angle: Double, parent: Vector3D): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).rotateYAroundParent(angle, parent)
   }
   /**
   Rotate all the Sprite3D Drawables in the scene on the z axis with an offset.
   Used particularly when rotating around camera.
   @param angle the angle of rotation on z axis.
   @param spriteOffset the distance from the parent position (and axis of rotation).
   This is the inverse magnitude vector of the difference between the child and 
   the parent.  So if child is at (4,0,0) and parent is at (0,0,0), the vector we
   need is (-4,0,0).  Calculate by subtracting the child from the parent.
   */
   def rotateZAroundParent(angle: Double, parent: Vector3D): Unit = {
      for (sprite <- sprites) (sprite.asInstanceOf[Sprite3D]).rotateZAroundParent(angle, parent)
   }

}
