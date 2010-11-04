/**
Sprite3D represents a 3D sprite with a location, size, star rotation, rotation offset (from center), animator and renderer
*/

package org.loom.scene

import java.awt.{Graphics2D,BasicStroke}
import java.awt.geom._
import java.awt.Polygon

import org.loom.geometry._
import org.loom.utility._

class Sprite3D(var shape: Shape3D, var location: Vector3D, var size: Vector3D, val startRotation: Vector3D, val rotOffset: Vector3D, var animator: Animator3D, var renderer: Renderer)  extends Drawable {

   var pointWidth: Double = 1//default width of point ellipses
   var pointHeight: Double = 1//default height of point ellipses

   shape.translate(rotOffset)//move shape not sprite because establishing rotation point
   scale(size)//at beginning (or whenever necessary)
   rotate(startRotation)//at beginning (or whenever necessary)

   /**
   Update - calls Animator.update
   */
   def update(): Unit = {
      animator.update(this)
   }
   /**
   Draw, draws a sprite
   Handles all sprite draw calls and passes them to specialised rendering methods depending on the rendering mode.
   @param g2D the Graphics2D context
   @param viewDistance the viewDistance is defined in the Camera
   */
   def draw(g2D: Graphics2D): Unit = {
      renderer.mode match {
         case Renderer.POINTS => drawPoints(g2D, Camera.viewDistance, Camera.view)
         case Renderer.LINES => drawLines(g2D, Camera.viewDistance, Camera.view)
         case Renderer.FILLED => drawFilled(g2D, Camera.viewDistance, Camera.view)
         case Renderer.FILLED_STROKED => drawFilledStroked(g2D, Camera.viewDistance, Camera.view)
      }
   }


   /**
   Translates the sprite (not the actual shape points).
   Scales the speed on the basis of the z coordinate.  This involves scaling the 
   x and y movement vectors on the basis of how far away the object is.
   @param speed the translation vector (Vector3D)
   */
   def translate(change: Vector3D): Unit = {
      location = Transform3D.translate(location, change)
   }
   /**
   Scale the sprite (affects the actual shape points).
   @param scale the scale vector (Vector3D)
   */
   def scale(scale: Vector3D): Unit = {
      shape.scale(scale)
   }
   /**
   Rotate the sprite (affects the actual shape points).
   @param rotation the rotation vector (Vector3D)
   */
   def rotate(rotation: Vector3D): Unit = {
      rotateX(rotation.x)
      rotateY(rotation.y)
      rotateZ(rotation.z)
   }
   /**
   Rotate the sprite x (affects the actual shape points).
   @param angle (Double)
   */
   def rotateX(angle: Double): Unit = {
      shape.rotateX(angle)
   }
   /**
   Rotate the sprite y (affects the actual shape points).
   @param angle (Double)
   */
   def rotateY(angle: Double): Unit = {
      shape.rotateY(angle)
   }
   /**
   Rotate the sprite z (affects the actual shape points).
   @param angle (Double)
   */
   def rotateZ(angle: Double): Unit = {
      shape.rotateZ(angle)
   }

   /**
   Rotate the sprite around a parent position.
   For rotating the sprite in relation to the camera, the parent position
   is the world origin (0,0,0), so just need to pass in the location of the 
   sprite as the spriteOffset value.  Note that this rotates the single location vector
   in the sprite, not the shape points (except for z rotation).
   @param rotation the rotation vector (Vector3D)
   @param spriteOffset the distance from the origin
   */
   def rotateAroundParent(rotation: Vector3D, parent: Vector3D): Unit = {
      rotateXAroundParent(rotation.x, parent)
      rotateYAroundParent(rotation.y, parent)
      rotateZAroundParent(rotation.z, parent)

   }

   def rotateXAroundParent(angle: Double, spriteOffset: Vector3D): Unit = {
      shape.rotateX(-angle)//rotate the shape
      location = Transform3D.rotateX(this.location, -angle, spriteOffset)//rotate the location
   }

   def rotateYAroundParent(angle: Double, spriteOffset: Vector3D): Unit = {
      shape.rotateY(angle)
      location = Transform3D.rotateY(this.location, angle, spriteOffset)
   }

   def rotateZAroundParent(angle: Double, spriteOffset: Vector3D): Unit = {
      shape.rotateZ(angle)
      location = Transform3D.rotateZ(this.location, angle, spriteOffset)
   }
   /**
   Clone the Sprite3D.  Produces an independent copy.
   @return Sprite3D
   */
   override def clone(): Sprite3D = {
      var s: Shape3D = shape.clone()
      var loc: Vector3D = location.clone()
      var sz: Vector3D = size.clone()
      var sr: Vector3D = startRotation.clone()
      var ro: Vector3D = rotOffset.clone()

      s.translate(Vector3D.invert(ro))//to return it back to original centred position
      val invertedStartRotation: Vector3D = Vector3D.invert(startRotation)
      s.rotateX(invertedStartRotation.x)//to return it back to original x rotation
      s.rotateY(invertedStartRotation.y)//to return it back to original y rotation
      s.rotateZ(invertedStartRotation.z)//to return it back to original z rotation
      s.scale(new Vector3D(1/sz.x, 1/sz.y, 1/sz.z))//to return it back to original scale

      var a: Animator3D = animator.clone()
      var r: Renderer = renderer.clone()
      new Sprite3D(s, loc, sz, sr, ro, a, r)
   }
   /**
   Check for intersection between this and another Sprite3D.
   @param the other Sprite3D
   @param dist the proximity that counts as intersection
   @return true (if intersection) false (if no intersection)
   */
   def checkIntersect(otherLocation: Sprite3D, dist: Double): Boolean = {
      val h: Double = Formulas.hypotenuse(location, otherLocation.location)
      if (h<=dist) true else false
   }

   def drawPoints(g2D: Graphics2D, viewDistance: Double, view: View): Unit = {
      g2D.setColor(renderer.strokeColor)
      g2D.setStroke(new BasicStroke(renderer.strokeWidth))
      for (poly <- shape.polys) {
         if (poly.getCenter().z + location.z > -(viewDistance)) {
            val poly2D: Polygon2D = PolygonCreator.convertPolygon3DToPolygon2D(poly)
            var count: Int = 0
            var perspective: Vector3D = new Vector3D(0,0,0)
            for (point <- poly.points) {
               if (!closerThanNearClipDistance(poly, viewDistance)) {
                  val p: Vector3D = new Vector3D(point.x + location.x, point.y + location.y, point.z + location.z)
                  perspective = Formulas.getPerspective(p, viewDistance)
                  val coordinateCorrection: Vector3D = view.viewToScreenVertex(perspective)
                  poly2D.points(count).x = coordinateCorrection.x
                  poly2D.points(count).y = coordinateCorrection.y
                  g2D.draw(new Ellipse2D.Double(poly2D.points(count).x.toInt, poly2D.points(count).y.toInt, pointWidth,pointHeight))
                  count += 1
               }
            }
         } else {
            //println("poly too close")
         }
      }
   }

   def drawLines(g2D: Graphics2D, viewDistance: Double, view: View): Unit = {
      for (poly <- shape.polys) {
         if (!closerThanNearClipDistance(poly, viewDistance)) {
            val poly2D: Polygon2D = PolygonCreator.convertPolygon3DToPolygon2D(poly)
            var count: Int = 0
            var perspective: Vector3D = new Vector3D(0,0,0)
            for (point <- poly.points) {
               val p: Vector3D = new Vector3D(point.x + location.x, point.y + location.y, point.z + location.z)
               perspective = Formulas.getPerspective(p, viewDistance)
               val coordinateCorrection: Vector3D = view.viewToScreenVertex(perspective)
               poly2D.points(count).x = coordinateCorrection.x
               poly2D.points(count).y = coordinateCorrection.y
               count += 1
            }
            val drawPoly: Polygon = PolygonCreator.convertPolygon2DToPolygon(poly2D)
            g2D.setColor(renderer.strokeColor)
            g2D.setStroke(new BasicStroke(renderer.strokeWidth))
            if (poly.polyType == PolygonType.Line_Polygon) {
               g2D.draw(drawPoly)
            } else {
            	//iterate through each of the spline segments in the spline Polygon3d
            	//println("poly sides total: " + poly2D.sidesTotal)
            	for (i <- 0 until poly2D.sidesTotal) {
		            val curve: CubicCurve2D.Float = new CubicCurve2D.Float(
		            		poly2D.points(0 + i*4).x.toFloat,
		            		poly2D.points(0 + i*4).y.toFloat,
		            		poly2D.points(1 + i*4).x.toFloat,
    	                    poly2D.points(1 + i*4).y.toFloat,
    	                    poly2D.points(2 + i*4).x.toFloat,
    	                    poly2D.points(2 + i*4).y.toFloat,
    	                    poly2D.points(3 + i*4).x.toFloat,
    	                    poly2D.points(3 + i*4).y.toFloat
		            		)
                    g2D.draw(curve)
            	}
            }
         } else {
            //println("poly too close")
         }
      }
   }

   def drawFilled(g2D: Graphics2D, viewDistance: Double, view: View): Unit = {
      for (poly <- shape.polys) {
         if (!closerThanNearClipDistance(poly, viewDistance)) {
            val poly2D: Polygon2D = PolygonCreator.convertPolygon3DToPolygon2D(poly)
            var count: Int = 0
            var perspective: Vector3D = new Vector3D(0,0,0)
            for (point <- poly.points) {
               val p: Vector3D = new Vector3D(point.x + location.x, point.y + location.y, point.z + location.z)
               perspective = Formulas.getPerspective(p, viewDistance)
               val coordinateCorrection: Vector3D = view.viewToScreenVertex(perspective)
               poly2D.points(count).x = coordinateCorrection.x
               poly2D.points(count).y = coordinateCorrection.y
               count += 1
            }
            val drawPoly: Polygon = PolygonCreator.convertPolygon2DToPolygon(poly2D)
            g2D.setColor(renderer.fillColor)
            if (poly.polyType == PolygonType.Line_Polygon) {
               g2D.fill(drawPoly)
            } else {
            	//iterate through each of the spline segments in the spline Polygon3d
            	//println("poly sides total: " + poly2D.sidesTotal)
            	val path: GeneralPath = new GeneralPath(Path2D.WIND_EVEN_ODD)
            	path.moveTo(poly2D.points(0).x.toFloat, poly2D.points(0).y.toFloat)
            	for (i <- 0 until poly2D.sidesTotal) {
    	            val c1X: Float = poly2D.points(1 + i*4).x.toFloat
    	            val c1Y: Float = poly2D.points(1 + i*4).y.toFloat
    	            val c2X: Float = poly2D.points(2 + i*4).x.toFloat
    	            val c2Y: Float = poly2D.points(2 + i*4).y.toFloat
    	            val a2X: Float = poly2D.points(3 + i*4).x.toFloat
    	            val a2Y: Float = poly2D.points(3 + i*4).y.toFloat
		            path.curveTo(c1X, c1Y, c2X, c2Y, a2X, a2Y);
            	}
            	g2D.fill(path)
            }
         } else {
            //println("poly too close")
         }
      }
   }

   def drawFilledStroked(g2D: Graphics2D, viewDistance: Double, view: View): Unit = {
      for (poly <- shape.polys) {
         if (!closerThanNearClipDistance(poly, viewDistance)) {
            val poly2D: Polygon2D = PolygonCreator.convertPolygon3DToPolygon2D(poly)
            var count: Int = 0
            var perspective: Vector3D = new Vector3D(0,0,0)
            for (point <- poly.points) {
               val p: Vector3D = new Vector3D(point.x + location.x, point.y + location.y, point.z + location.z)
               perspective = Formulas.getPerspective(p, viewDistance)
               val coordinateCorrection: Vector3D = view.viewToScreenVertex(perspective)
               poly2D.points(count).x = coordinateCorrection.x
               poly2D.points(count).y = coordinateCorrection.y
               count += 1
            }
            val drawPoly: Polygon = PolygonCreator.convertPolygon2DToPolygon(poly2D)

            if (poly.polyType == PolygonType.Line_Polygon) {
            	g2D.setColor(renderer.fillColor)
                g2D.fill(drawPoly)
                g2D.setColor(renderer.strokeColor)
                g2D.setStroke(new BasicStroke(renderer.strokeWidth))
                g2D.draw(drawPoly)
            } else {
            	//iterate through each of the spline segments in the spline Polygon3d
            	//println("poly sides total: " + poly2D.sidesTotal)
            	val path: GeneralPath = new GeneralPath(Path2D.WIND_EVEN_ODD)
            	path.moveTo(poly2D.points(0).x.toFloat, poly2D.points(0).y.toFloat)
            	for (i <- 0 until poly2D.sidesTotal) {
    	            val c1X: Float = poly2D.points(1 + i*4).x.toFloat
    	            val c1Y: Float = poly2D.points(1 + i*4).y.toFloat
    	            val c2X: Float = poly2D.points(2 + i*4).x.toFloat
    	            val c2Y: Float = poly2D.points(2 + i*4).y.toFloat
    	            val a2X: Float = poly2D.points(3 + i*4).x.toFloat
    	            val a2Y: Float = poly2D.points(3 + i*4).y.toFloat
		            path.curveTo(c1X, c1Y, c2X, c2Y, a2X, a2Y);
            	}
                g2D.setColor(renderer.fillColor)
                g2D.fill(path)
                g2D.setColor(renderer.strokeColor)
                g2D.setStroke(new BasicStroke(renderer.strokeWidth))
                g2D.draw(path)
            }
         } else {
            //println("poly too close")
         }
      }
   }
   def closerThanNearClipDistance(poly: Polygon3D, viewDistance: Double): Boolean = {
      var tooClose: Boolean = false
      for (point <- poly.points) {
         if (point.z + location.z <= -viewDistance) tooClose = true
      }
      tooClose
   }
   /**
   toString - gets location, size, startRotation, rotOffset
   @return String representation of Sprite3D properties
   */
   override def toString(): String = "Sprite3D location: (" + location.x + ", " + location.y + ", " + location.z + ")  size: (" + 
      size.x + ", " + size.y + ", " + size.z + ")  start rotation: " + startRotation + "  rotOffset: (" + rotOffset.x + ", " + rotOffset.y + ", " + rotOffset.z + ")"

}

