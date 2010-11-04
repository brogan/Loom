/**
KeyPressListener
*/

package org.loom.interaction

import java.awt.event._
import org.loom.scene.Renderer

class KeyPressListener(val interactionManager: InteractionManager) extends KeyListener {

   /**
   * keyPressed
   * @param e
   */
   override def keyPressed(e: KeyEvent): Unit = {
      val keyCode: Int = e.getKeyCode()
      //println("Key code:" + keyCode)
      keyCode match {
         case KeyEvent.VK_SHIFT => interactionManager.shiftKey = true
         case KeyEvent.VK_CONTROL => interactionManager.controlKey = true
         case KeyEvent.VK_SPACE => println("space bar pressed")
         case KeyEvent.VK_LEFT => interactionManager.moveLeft()
         case KeyEvent.VK_RIGHT => interactionManager.moveRight()
         case KeyEvent.VK_UP => interactionManager.moveUp()
         case KeyEvent.VK_DOWN => interactionManager.moveDown()
         case KeyEvent.VK_1 => interactionManager.switchRenderingMode(Renderer.POINTS)
         case KeyEvent.VK_2 => interactionManager.switchRenderingMode(Renderer.LINES)
         case KeyEvent.VK_3 => interactionManager.switchRenderingMode(Renderer.FILLED)
         case KeyEvent.VK_4 => interactionManager.switchRenderingMode(Renderer.FILLED_STROKED)
         case KeyEvent.VK_P => interactionManager.togglePause()
         case KeyEvent.VK_Q => interactionManager.quit()
         case _ => //println("not a valid key released code")
      }	
   }
   /**
   * keyTyped
   * @param e
   */	
   override def keyTyped(e: KeyEvent): Unit = {
      val keyChar: Char = e.getKeyChar()
      //println("Key char:" + keyChar)
   }
   /**
   * keyReleased
   * @param e
   */	
   override def keyReleased(e: KeyEvent): Unit = {
      val keyCode: Int = e.getKeyCode()
      //println("Key code:" + keyCode)
      keyCode match {
         case KeyEvent.VK_SHIFT => interactionManager.shiftKey = false
         case KeyEvent.VK_CONTROL => interactionManager.controlKey = false
         case _ => //println("not a valid key released code")
      }

   }

}
