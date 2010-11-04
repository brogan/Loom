/**
DrawPanel
*/

package org.loom.scaffold

import javax.swing._
import java.awt._
import java.awt.event._
import org.loom.interaction._

class DrawPanel() extends JPanel {

    println("DrawPanel loaded")

    var paused: Boolean = false
    val drawManager: DrawManager = new DrawManager()

    val interactionManager: InteractionManager = new InteractionManager(drawManager)

    val keyL: KeyListener = new KeyPressListener(interactionManager)
    addKeyListener(keyL)

    val mC: MouseAdapter = new MouseClick(interactionManager).asInstanceOf[MouseAdapter]
    addMouseListener(mC)

    val mML: MouseMotionListener = new MouseMotion(interactionManager).asInstanceOf[MouseMotionListener]
    addMouseMotionListener(mML)

    setFocusable(true)

    var dBuffer: Image = null
    AnimationActor.setDrawPanel(this)
    AnimationActor.start()
	
    def animationUpdate(): Unit = {
        //println("updating animation");
        if (!paused) {
           drawManager.update()
        }
    }
    
    def animationRender(): Unit = {
        //println("rendering animation");
        if (dBuffer == null) {//only runs first time through
           dBuffer = createImage(Config.width, Config.height);
        } else {
            var dBufferGraphics = dBuffer.getGraphics();
            drawManager.draw(dBufferGraphics);
            var g: Graphics = this.getGraphics();
            paintScreen(g);
        }
    }

    def paintScreen(g: Graphics): Unit = {
        var g2D: Graphics2D = g.asInstanceOf[Graphics2D]
        if (dBuffer != null) {
            g2D.drawImage(dBuffer, 0, 0, null)
        } else {
            System.out.println("unable to create double buffer")
        }
    }
}
