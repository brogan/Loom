/**
ShapeCreator
Convenience methods for creating shapes
*/

package org.loom.geometry

import org.loom.utility._

object ShapeCreator {

    /**
   Makes a crystaline 3D shape that has a single point at both the top and bottom
   and a ring of middle points that provide the basis for a ring of triangular polygons that link
   to both the top and bottom points.
   @param numHoriz the number of points in the middle ring
   @return Shape3D
   */
   def makeCrystal3DShape(numHoriz: Int): Shape3D = {
      val vertMiddlePoints: Array[Vector3D] = new Array[Vector3D](numHoriz)
      val vertAngle: Double = 360.0/numHoriz
      val topPoint: Vector3D = new Vector3D(0,-1,0)
      val frontPoint: Vector3D = new Vector3D(0,0,1)
      val backPoint: Vector3D = new Vector3D(0,0,-1)
      vertMiddlePoints(0) = topPoint
      for(i <- 1 until numHoriz) {
         vertMiddlePoints(i) = Transform3D.rotateZ(vertMiddlePoints(i-1), vertAngle) 
      }
      val polys: Array[Polygon3D] = new Array[Polygon3D](numHoriz*2)
      for(i <- 0 until numHoriz) {
         if (i < numHoriz-1) {
            polys(i) = new Polygon3D(List(vertMiddlePoints(i), backPoint, vertMiddlePoints(i+1)), PolygonType.Line_Polygon)
         } else {
            polys(i) = new Polygon3D(List(vertMiddlePoints(i), backPoint, vertMiddlePoints(0)), PolygonType.Line_Polygon)
         }
      }
      var count: Int = 0
      for(i <- numHoriz until numHoriz*2) {
         if (i < (numHoriz*2)-1) {
            polys(i) = new Polygon3D(List(vertMiddlePoints(count), frontPoint, vertMiddlePoints(count+1)), PolygonType.Line_Polygon)
         } else {
            polys(i) = new Polygon3D(List(vertMiddlePoints(count), frontPoint, vertMiddlePoints(0)), PolygonType.Line_Polygon)
         }
         count+=1
      }
      //get the shape points in a single array
      val points: Array[Vector3D] = new Array[Vector3D](vertMiddlePoints.length + 2)
      points(0) = frontPoint
      for (i <- 1 until points.length -1) {
         points(i) = vertMiddlePoints(i-1)
      }
      points(points.length-1) = backPoint
      new Shape3D(points.toList, polys.toList)
   }
   /**
   Makes a shape that has two parallel polygonal planes and any number
   of polygons that link the edges of the two planes.  You can create a cube-like
   shape by specifying 4 sides to each plane, etc.
   @param numHoriz the number of points on each of the parallel polygonal planes
   @return Shape3D
   */
   def makeRect3DShape(numHoriz: Int): Shape3D = {
      val vertBackPoints: Array[Vector3D] = new Array[Vector3D](numHoriz)
      val vertFrontPoints: Array[Vector3D] = new Array[Vector3D](numHoriz)
      val vertAngle: Double = 360.0/numHoriz
      val startPoint: Vector3D = new Vector3D(0,-1,-1)
      vertBackPoints(0) = startPoint.clone()
      vertFrontPoints(0) = startPoint.clone()
      val transformDistanceZ: Double = (Formulas.adjacent(1,vertAngle))*2//need length of adjacent side (not hypotenuse (of 1))
      vertFrontPoints(0) = Transform3D.translate(vertFrontPoints(0), new Vector3D(0,0,transformDistanceZ))
      for(i <- 1 until numHoriz) {
         vertBackPoints(i) = Transform3D.rotateZ(vertBackPoints(i-1), vertAngle) 
         vertFrontPoints(i) = vertBackPoints(i).clone
         vertFrontPoints(i) = Transform3D.translate(vertFrontPoints(i), new Vector3D(0,0,transformDistanceZ))
      }
      val polys: Array[Polygon3D] = new Array[Polygon3D](numHoriz+2)
      for(i <- 0 until numHoriz) {
         if (i < numHoriz-1) {
            polys(i) = new Polygon3D(List(vertBackPoints(i), vertFrontPoints(i), vertFrontPoints(i+1), vertBackPoints(i+1)), PolygonType.Line_Polygon)
         } else {
            polys(i) = new Polygon3D(List(vertBackPoints(i), vertFrontPoints(i), vertFrontPoints(0), vertBackPoints(0)), PolygonType.Line_Polygon)
         }
      }
      polys(polys.length-2) = new Polygon3D(vertBackPoints.toList, PolygonType.Line_Polygon)
      polys(polys.length-1) = new Polygon3D(vertFrontPoints.toList, PolygonType.Line_Polygon)

      //get the shape points in a single array
      val points: Array[Vector3D] = new Array[Vector3D](numHoriz * 2)
      for (i <- 0 until numHoriz) {
         points(i) = vertFrontPoints(i)
      }
      for (j <- numHoriz until numHoriz * 2) {
         points(j) = vertBackPoints(j - numHoriz)
      }
      new Shape3D(points.toList, polys.toList)
   }

   /**
   Extrudes a Polygon2D on the z axis
   @param poly2D the Polygon2D to extrude
   @return Shape3D
   */
   def extrudePolygon2D(poly2D: Polygon2D): Shape3D = {
      val numHoriz: Int = poly2D.points.size
      val backPoints: Array[Vector3D] = convertPolygon2DToArrayVector3D(poly2D)
      val frontPoints: Array[Vector3D] = convertPolygon2DToArrayVector3D(poly2D)
      for (point <- backPoints) point.translate(new Vector3D(0,0,-.5))
      for (point <- frontPoints) point.translate(new Vector3D(0,0,.5))
      val polys: Array[Polygon3D] = new Array[Polygon3D](numHoriz+2)
      for (i <- 0 until numHoriz) {
         if (i < numHoriz-1) {
            polys(i) = new Polygon3D(List(backPoints(i), frontPoints(i), frontPoints(i+1), backPoints(i+1)), PolygonType.Line_Polygon)
         } else {
            polys(i) = new Polygon3D(List(backPoints(i), frontPoints(i), frontPoints(0), backPoints(0)), PolygonType.Line_Polygon)
         }
      }
      polys(numHoriz) = new Polygon3D(backPoints.toList, PolygonType.Line_Polygon)
      polys(numHoriz+1) = new Polygon3D(frontPoints.toList, PolygonType.Line_Polygon)

      //get the shape points in a single array
      val points: Array[Vector3D] = new Array[Vector3D](numHoriz * 2)
      for (i <- 0 until numHoriz) {
         points(i) = backPoints(i)
      }
      for (j <- numHoriz until numHoriz*2) {
         points(j) = frontPoints(j - numHoriz)
      }
      new Shape3D(points.toList, polys.toList)
   }

   /**
   Extrudes a Polygon2D on the z axis any number of times
   @param poly2D the Polygon2D to extrude
   @param extrudes array (this example makes a small house):
   val extrudes: Array[Extrude] = new Array[Extrude] (3)
   //extrude - translation, scale, rotation
   extrudes(0) = new Extrude(new Vector3D(0,0,.6), new Vector3D(1,1,1), new Vector3D(0,0,0))//base walls
   extrudes(1) = new Extrude(new Vector3D(0,0,.6), new Vector3D(1.5,1.5,1), new Vector3D(0,0,0))//roofline
   extrudes(2) = new Extrude(new Vector3D(0,0,1), new Vector3D(0,0,1), new Vector3D(0,0,0))//roof apex
   @param rotOffsetStrategy: Extrude.ROTATE_TOP, Extrude.ROTATE_CENTER, Extrude.ROTATE_BOTTOM 
   @return Shape3D
   */
   def extrudePolygon2D(poly2D: Polygon2D, extrudes: Array[Extrude], rotOffsetStrategy: Int): Shape3D = {
      val numPoints: Int = poly2D.points.size
      val allPoints: Array[Array[Vector3D]] = new Array[Array[Vector3D]](extrudes.length + 1)
      allPoints(0) = convertPolygon2DToArrayVector3D(poly2D)
      for (i <- 0 until extrudes.length) {
         allPoints(i+1) = convertPolygon2DToArrayVector3D(poly2D)
         for (j <- 0 until numPoints) {
            allPoints(i+1)(j).scale(extrudes(i).scale)
            allPoints(i+1)(j).rotateX(extrudes(i).rotation.x)
            allPoints(i+1)(j).rotateY(extrudes(i).rotation.y)
            allPoints(i+1)(j).rotateZ(extrudes(i).rotation.z)
            allPoints(i+1)(j).translate(extrudes(i).translation)

         }
      }
      val polys: Array[Polygon3D] = new Array[Polygon3D]((numPoints * (allPoints.length-1))+2)
      for (i <- 0 until extrudes.length) {
         for (j <- 0 until numPoints) {
            val curr: Int = (i * numPoints) + j
            println("curr: " + curr + "  i: " + i + "  j: " + j)
            if (j < numPoints-1) {
               polys(curr) = new Polygon3D(List(allPoints(i)(j), allPoints(i + 1)(j), allPoints(i + 1)(j + 1), allPoints(i)(j + 1)), PolygonType.Line_Polygon)
            } else {
               polys(curr) = new Polygon3D(List(allPoints(i)(j), allPoints(i+1)(j), allPoints(i+1)(0), allPoints(i)(0)), PolygonType.Line_Polygon)
            }
         }
      }
      var zDepthTotal: Double = 0
      val row: Array[Vector3D] = allPoints(allPoints.length-1)
      for(i <- 0 until row.length) {
         zDepthTotal += row(i).z
      }
      zDepthTotal = zDepthTotal/numPoints  
      rotOffsetStrategy match {
         case Extrude.ROTATE_TOP => zDepthTotal
         case Extrude.ROTATE_CENTER => zDepthTotal = zDepthTotal/2
         case Extrude.ROTATE_BOTTOM => zDepthTotal = 0
      }
      val aP: Array[Vector3D] = new Array[Vector3D]((numPoints * (allPoints.length)))
      for (i <- 0 until allPoints.length) {
         for (j <- 0 until numPoints) {
            val curr: Int = (i * numPoints) + j
            aP(curr) = allPoints(i)(j)
            aP(curr).translate(new Vector3D(0,0,-(zDepthTotal)))//for rotation
         }
      }
      val backPoints: Array[Vector3D] = new Array[Vector3D](numPoints)
      val frontPoints: Array[Vector3D] = new Array[Vector3D](numPoints)
      for (i <- 0 until numPoints) {
         backPoints(i) = aP(i)
      }
      var counter: Int = 0
      for (i <- (numPoints*extrudes.length) until (numPoints*(extrudes.length+1))) {
         frontPoints(counter) = aP(i)
         counter += 1
      }
      polys(numPoints * (allPoints.length-1)) = new Polygon3D(backPoints.toList, PolygonType.Line_Polygon)
      polys((numPoints * (allPoints.length-1)) + 1) = new Polygon3D(frontPoints.toList, PolygonType.Line_Polygon)
      for (poly <- polys) {
         println(poly)
      }
      new Shape3D(aP.toList, polys.toList)
   }

   def convertPolygon2DToArrayVector3D(pol: Polygon2D): Array[Vector3D] = {
      val a3D: Array[Vector3D] = new Array[Vector3D](pol.points.size)
      for (i <- 0 until pol.points.size) {
         a3D(i) = new Vector3D(pol.points(i).x, pol.points(i).y, 0)//CHANGED FROM -.5 (WILL CAUSE PROBLEMS WITH OLDER SKETCHES)
      }
      a3D
   }

   /**
   Makes a grid plane centred on 0, 0 with specified number of rows and columns.
   Note: if using within the context of a deforming the grid plane with a height map,
   the height map needs to have one more row and column than the grid plane, because
   the height map counts points (a row of four columns requires 5 points, etc.)
   @param rows
   @param cols
   @return Shape3D
   */
   def makeGridPlane(rows: Int, cols: Int): Shape3D = {
      val points: Array[Vector3D] = new Array[Vector3D]((rows+1) * (cols+1))
      val polys: Array[Polygon3D] = new Array[Polygon3D](rows * cols)
      for (r <- 0 to rows) {
         for (c <- 0 to cols) {
            points((r*(rows+1))+c) = new Vector3D(c-(cols/2),0,r-(rows/2))
            //println((r*(rows+1))+c + ": " + points((r*(rows+1))+c))
         }
      }
      for (r <- 0 until rows) {
         for (c <- 0 until cols) {
            val topLeft: Vector3D = points((r*(rows+1)) +c)
            val topRight: Vector3D = points((r*(rows+1)) +c+1)
            val bottomRight: Vector3D = points(((r+1)*(rows+1))+c+1)
            val bottomLeft: Vector3D = points(((r+1)*(rows+1))+c)
            polys((r*rows)+c) = new Polygon3D(List(topLeft, topRight, bottomRight, bottomLeft), PolygonType.Line_Polygon)
         }
      }
      new Shape3D(points.toList, polys.toList)
   }

   /**
   Makes a grid block centred on 0, 0 with specified number of rows, columns and layers.
   Note: if using within the context of a deforming the grid plane with a height map,
   the height map needs to have one more row and column than the grid plane, because
   the height map counts points (a row of four columns requires 5 points, etc.)
   @param rows
   @param cols
   @param layers
   @return Shape3D
   */
   def makeGridBlock(rows: Int, cols: Int, layers: Int): Shape3D = {
      println("Making grid block")
      val points: Array[Vector3D] = new Array[Vector3D]((rows+1) * (cols+1) * (layers + 1))
      //println("points length: " + points.length)
      //the poly build section is one code block below but needs to be explained in terms of
      //how the number of polygons is calculated.
      //each layer build 5 sides of cube starting with the vertical poly defined by the topleft, topright, etc.
      //points below, then building the sides that connect horizontally to the next layer back.  The final layer
      //just builds its vertical polygons given as rows & cols because no need build back another layer.
      val polys: Array[Polygon3D] = new Array[Polygon3D]((rows * cols * layers * 6))
      //println("polys length: " + polys.length)
      for (l <- 0 to layers) {
         for (r <- 0 to rows) {
            for (c <- 0 to cols) {
               val currIndex = ((r * (rows+1)) + c) + (l * (rows+1) * (cols+1))
               points(currIndex) = new Vector3D(c-(cols/2),l-(layers/2),r-(rows/2))
               //println(currIndex + ": " + points(currIndex))
            }
         }
      }
      var currPolyIndex: Int = 0
      for (l <- 0 until layers) {
         for (r <- 0 until rows) {
            for (c <- 0 until cols) {
               val currIndex = ((r * (rows+1)) + c) + (l * (rows+1) * (cols+1))
               //println("curr point index: " + currIndex)
               val topLeft: Vector3D = points(currIndex)
               val topRight: Vector3D = points(currIndex + 1)
               val bottomRight: Vector3D = points(currIndex + (rows+1) + 1)
               val bottomLeft: Vector3D = points(currIndex + (rows+1))
               val topLeftBack: Vector3D = points(currIndex + ((rows+1) * (cols+1)))
               val topRightBack: Vector3D = points(currIndex + 1 + ((rows+1) * (cols+1)))
               val bottomRightBack: Vector3D = points(currIndex + (rows+1) + 1 + ((rows+1) * (cols+1)))
               val bottomLeftBack: Vector3D = points(currIndex + (rows+1) + ((rows+1) * (cols+1)))

               polys(currPolyIndex) = new Polygon3D(List(topLeft, topRight, bottomRight, bottomLeft), PolygonType.Line_Polygon)
               polys(currPolyIndex+1) = new Polygon3D(List(topLeft, topLeftBack, topRightBack, topRight), PolygonType.Line_Polygon)
               polys(currPolyIndex+2) = new Polygon3D(List(topRight, topRightBack, bottomRightBack, bottomRight), PolygonType.Line_Polygon)
               polys(currPolyIndex+3) = new Polygon3D(List(bottomLeft, bottomLeftBack, bottomRightBack, bottomRight), PolygonType.Line_Polygon)
               polys(currPolyIndex+4) = new Polygon3D(List(topLeft, topLeftBack, bottomLeftBack, bottomLeft), PolygonType.Line_Polygon)
               polys(currPolyIndex+5) = new Polygon3D(List(topLeftBack, topRightBack, bottomRightBack, bottomLeftBack), PolygonType.Line_Polygon)
               currPolyIndex = currPolyIndex + 6
               //println("curr poly index: " + currPolyIndex)
            }
         }
      }
      new Shape3D(points.toList, polys.toList)
   }

}
