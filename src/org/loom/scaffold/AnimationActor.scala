/**
AnimationActor
*/

package org.loom.scaffold

import scala.actors._

object AnimationActor extends Actor {

    var paused: Boolean = false
    var drawPanel: DrawPanel = null

    def act() {
       loop {
          if (!paused) {
    	     //System.out.println("Here we go");
    	     drawPanel.animationUpdate();
    	     drawPanel.animationRender();//to a buffer
    	     Thread.sleep(20);
    	  }
       }
    }

    def setDrawPanel(dP: DrawPanel): Unit = {
       drawPanel = dP
    }
	
    def setPaused(): Unit = {
    	if (paused) {
    	   paused = false;
    	} else {
    	   paused = true;
    	}
    }

}
