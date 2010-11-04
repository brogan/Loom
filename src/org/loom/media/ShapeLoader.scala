package org.loom.media

import org.loom.geometry._

import scala.xml._
import scala.collection.mutable.ListBuffer


object ShapeLoader {
	
	def load2DShape(sketchName: String, shapeType: String, shapeName: String): List[Vector2D] = {
	   var shape: Seq[Node] = null
	   val points: ListBuffer[Vector2D] = new ListBuffer[Vector2D]()
       shape = XML.loadFile(ProjectFilePath.filePath + ProjectFilePath.separator + "sketches" + ProjectFilePath.separator + sketchName + ProjectFilePath.separator + "resources" + ProjectFilePath.separator + "shapes" + ProjectFilePath.separator + shapeType + ProjectFilePath.separator + shapeName)
       val polyPoints: NodeSeq = (shape \\ "polyPoint")
       println("polyPoints.length: " + polyPoints.length)
       for (i <- 0 until polyPoints.length) {
    	   val point: NodeSeq = polyPoints(i)
    	   val x: String = (point \ "x").text
    	   val xD: Double = x.toDouble
    	   val y: String = (point \ "y").text
    	   val yD: Double = y.toDouble
    	   println("y: " + y)
    	   points += new Vector2D(xD, yD)
       }
       points.toList
   }
	
    def load3DShape(sketchName: String, shapeType: String, shapeName: String): List[Vector3D] = {
	   var shape: Seq[Node] = null
	   val points: ListBuffer[Vector3D] = new ListBuffer[Vector3D]()
       shape = XML.loadFile(ProjectFilePath.filePath + ProjectFilePath.separator + "sketches" + ProjectFilePath.separator + sketchName + ProjectFilePath.separator + "resources" + ProjectFilePath.separator + "shapes" + ProjectFilePath.separator + shapeType + ProjectFilePath.separator + shapeName)
       val polyPoints: NodeSeq = (shape \\ "polyPoint")
       println("polyPoints.length: " + polyPoints.length)
       for (i <- 0 until polyPoints.length) {
    	   val point: NodeSeq = polyPoints(i)
    	   val x: String = (point \ "x").text
    	   val xD: Double = x.toDouble
    	   val y: String = (point \ "y").text
    	   val yD: Double = y.toDouble
    	   println("y: " + y)
    	   points += new Vector3D(xD, yD, 0)
       }
       points.toList
   }

}