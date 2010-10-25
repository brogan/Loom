/**
Scene2D
*/

package org.loom.scene

import org.loom.geometry._

import java.awt.Graphics2D
import scala.collection.mutable.ListBuffer

class Scene2D() {


   val sprites: ListBuffer[Sprite2D] = new ListBuffer[Sprite2D]()

   /**
   Adds a Sprite2D to the scene.
   @param sprite the Sprite2D to store
   */
   def addSprite(sprite: Sprite2D): Unit = {
      sprites += sprite
   }

   /**
   Adds a Sprite2D to the scene.
   @param sprite the Sprite2D to store
   @param zIndex the zIndex of the sprite to store
   */
   def addSprite(sprite: Sprite2D, zIndex: Int): Unit = {
      sprites.insert(zIndex, sprite)
   }

   /**
   Removes a Sprite2D from the scene.
   @param sprite the Sprite2D to remove
   */
   def removeSprite(sprite: Sprite2D): Unit = {
      sprites -= sprite
   }

   /**
   Removes a Sprite2D from the scene.
   @param the zIndex of the sprite to remove
   */
   def removeSprite(zIndex: Int): Unit = {
      sprites.remove(zIndex)
   }

   /**
   Gets Sprite2D index in scene.
   @param sprite the Sprite2D to get index
   @return index (Int)
   */
   def getIndex(sprite: Sprite2D): Int = {
      var index: Int = -1
      for (i <- 0 until sprites.size) {
         if (sprites(i) == sprite) {
           index = i
         }
      }
      index
   }
   /**
   Change a Sprite2D zIndex in the scene
   @param sprite the Sprite2D to change index
   @param zIndex the new zIndex of the sprite
   */
   def changeZIndex(sprite: Sprite2D, zIndex: Int): Unit = {
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
   Draw, draws a Scene2D
   Calls draw on all sprites in the scene
   @param g2D the Graphics2D context
   */
   def draw(g2D: Graphics2D): Unit = {
      for (sprite <- sprites) sprite.draw(g2D)
   }
   /**
   Update
   Calls update on all sprite2Ds in the scene
   */
   def update(): Unit = {
      for (sprite <- sprites) sprite.update()
   }
   /**
   Translate all the Sprite2Ds in the scene
   @param speed the translation vector (Vector2D)
   */
   def translate(speed: Vector2D): Unit = {
      for (sprite <- sprites) sprite.translate(speed)
   }
   /**
   Scale all the Sprite2Ds in the scene
   @param scale the scale vector (Vector2D)
   */
   def scale(scale: Vector2D): Unit = {
      for (sprite <- sprites) sprite.scale(scale)
   }
   /**
   Rotate all the Sprite2Ds in the scene
   @param angle the angle of rotation on x axis.
   */
   def rotate(angle: Double): Unit = {
      for (sprite <- sprites) sprite.rotate(angle)
   }
   /**
   */
   def rotateAroundParent(rotation: Vector2D, parent: Vector3D): Unit = {
      //for (sprite <- sprites) sprite.rotateAroundParent(rotation, parent)
   }
 
}

