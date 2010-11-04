package org.loom.scene

import org.loom.geometry._
import java.awt._

trait Drawable {
	
	def update(): Unit
	def draw (g2D: Graphics2D): Unit

}