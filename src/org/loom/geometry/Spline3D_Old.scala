package org.loom.geometry

import org.loom.utility._
import org.loom.scene._
import java.awt.{Graphics2D, Color, BasicStroke}
import java.awt.geom._

class Spline3D_Old(val anchorPoints: List[Vector3D]) {

	val sidesTotal: Int = anchorPoints.length - 1
	val points: Array[Vector3D] = new Array[Vector3D](sidesTotal * 4)
	val anchorCount = 0
	var count: Int = 0
	for (i <- 0 until points.length) {
		points(count) = anchorPoints(anchorCount)	
		if (i % 2 == 0) {
			count += 3
		} else {
			count += 1
		}
	}
	setControlPoints()
	
	override def toString(): String = "Spline3D sidesTotal: " + sidesTotal
	def print(): Unit = { println("\n" + this.toString()); for (point <- points) println(point) }
    /**
    * calculates intermediate control points when initially placing anchor points
    */
    def setControlPoints(): Unit = {
		
        val diffX: Double = points(3).x-points(0).x
        val diffY: Double = points(3).y-points(0).y
        val diffZ: Double = points(3).z-points(0).z
        val incX: Double = diffX/3
        val incY: Double = diffY/3
        val incZ: Double = diffZ/3
        points(1) = new Vector3D(points(0).x+incX, points(0).y+incY, points(0).z+incZ)
        points(2) = new Vector3D(points(0).x+(2 * incX), points(0).y+(2 * incY), points(0).z+(2 * incZ))
        points(1).rotateZ(45)//totally arbitrary
        points(1).translate(new Vector3D(-300,200,0))//totally arbitrary
        points(2).rotateZ(45)//totally arbitrary
        points(2).translate(new Vector3D(300,-200,0))//totally arbitrary
    }
	def draw(g2D: Graphics2D): Unit = {
		var ps: Array [Vector3D] = new Array[Vector3D](4)
    	for (i <- 0 until ps.length) {
    		ps(i) = points(i).clone()
    	}
		for (p <- ps) {
            val perspective = Formulas.getPerspective(p, Camera.viewDistance)
            val coordinateCorrection: Vector3D = Camera.view.viewToScreenVertex(perspective)
            p.x = coordinateCorrection.x
            p.y = coordinateCorrection.y
        }
    	val a1X: Float = ps(0).x.toFloat
    	val a1Y: Float = ps(0).y.toFloat
    	val c1X: Float = ps(1).x.toFloat
    	val c1Y: Float = ps(1).y.toFloat
    	val c2X: Float = ps(2).x.toFloat
    	val c2Y: Float = ps(2).y.toFloat
    	val a2X: Float = ps(3).x.toFloat
    	val a2Y: Float = ps(3).y.toFloat
		val curve: CubicCurve2D.Float = new CubicCurve2D.Float(a1X,a1Y,c1X,c1Y,c2X,c2Y,a2X,a2Y);
    	g2D.setColor(new Color(255,255,255,20))
        g2D.setStroke(new BasicStroke(.05f))
        g2D.draw(curve);
	}
   /**
   Translate the Spline3D by an x, y, z vector(Vector3D).  Goes through all the points in the spline and translates them.
   @param trans the translation vector expressed as a Vector3D
   */
   def translate(trans: Vector3D): Unit = {
      for (point <- points)point.translate(trans)
   }
   /**
   Scale the Spline3D by an x, y, z vector(Vector3D).  Goes through all the points in the spline and scales them.
   @param scale the scale vector expressed as a Vector3D
   */
   def scale(scale: Vector3D): Unit = {
      for (point <- points)point.scale(scale)
   }
   /**
   Rotate the Spline3D on the x axis by an angle.  Goes through all the points in the spline and rotates them.
   @param angle the angle of rotation on x axis
   */
   def rotateX(angle: Double): Unit = {
      for (point <- points)point.rotateX(angle)
   }
   /**
   Rotate the Spline3D on the y axis by an angle.  Goes through all the points in the spline and rotates them.
   @param angle the angle of rotation on y axis
   */
   def rotateY(angle: Double): Unit = {
      for (point <- points)point.rotateY(angle)
   }
   /**
   Rotate the Spline3D on the z axis by an angle.  Goes through all the points in the spline and rotates them.
   @param angle the angle of rotation on z axis
   */
   def rotateZ(angle: Double): Unit = {
      for (point <- points)point.rotateZ(angle)
   }

   /**
   Get central point
   @return center (Vector3D)
   */
   def getCenter(): Vector3D = {
      var totX: Double = 0
      var totY: Double = 0
      var totZ: Double = 0
      for (point <- points) {
         totX += point.x
         totY += point.y
         totZ += point.z
      }
      totX = totX/points.length
      totY = totY/points.length
      totZ = totZ/points.length
      new Vector3D(totX, totY, totZ)
   }
   /**
   This clones the Spline3D in such a way that the new spline shares the
   same Vector3D references (points) as the original spline
   @return Spline3D (shallow copy)
   */
   override def clone(): Spline3D_Old = {
      val copy: Array[Vector3D] = new Array[Vector3D](sidesTotal)
      var i: Int = 0
      for (point <- points) { copy(i) = point; i += 1 }
      new Spline3D_Old(copy.toList)
   }
   /**
   This creates a deep clone of the spline, so that the spline refers
   to a new set of points.
   @param newPoints the new set of points for the overall new Spline3D
   @param vertexOrder the array of indices in newPoints relevant to this spline
   @return Spline3D (independent copy)
   */
   def clone(newPoints: Array[Vector3D], vertexOrder: Array[Int]): Spline3D_Old = {
      val copyPoly: Array[Vector3D] = new Array[Vector3D](sidesTotal)
      for (i <- 0 until points.size) copyPoly(i) = newPoints(vertexOrder(i))
      new Spline3D_Old(copyPoly.toList)
   }

}