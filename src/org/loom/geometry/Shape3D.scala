/**
Shape3D represents a 3D shape composed of multiple points (Vector3Ds) and Polygon3Ds.
The shape needs to store the overall list of points because polygons within an overall shape share points.
For this reason, Polygon3Ds store references to points rather than independent copies of points.  This makes transform
operations on shapes faster because there are less points to process.  It also means that transforming a single
polygon affects adjacent polygons, making the shape seem an integral object.
*/

package org.loom.geometry

class Shape3D(val points: List[Vector3D], val polys: List[Polygon3D]) {

   val pointsTotal: Int = points.length
   val polysTotal: Int = polys.length
   val vertexOrders: Array[Array[Int]] = new Array[Array[Int]](polysTotal)
   makeVertexOrders()
   var dimensions: Vector3D = new Vector3D(0,0,0)
   setDimensions()

   override def toString(): String = "Shape3D polysTotal: " + polysTotal
   def print(): Unit = { println("\n" + this.toString()); for (poly <- polys) println(poly) }
   /**
   Translate the Shape3D by an x, y, z vector (Vector3D).  Goes through all the points in the shape and translates them.
   @param trans the translation vector expressed as a Vector3D
   */
   def translate(trans: Vector3D): Unit = {
      for (point <- points) point.translate(trans)
   }
   /**
   Scale the Shape3D by an x, y, z vector (Vector3D).  Goes through all the points in the shape and scales them.
   @param scale the scale vector expressed as a Vector3D
   */
   def scale(scale: Vector3D): Unit = {
      for (point <- points) point.scale(scale) 
      setDimensions()
      //println("shape dimensions: " + dimensions)
   }
   /**
   Rotate the Shape3D on x axis by an angle.  Goes through all the points in the shape and rotates them on x axis.
   @param angle the rotation angle on x axis
   */
   def rotateX(angle: Double): Unit = {
      for (point <- points) point.rotateX(angle)
   }
   /**
   Rotate the Shape3D on y axis by an angle.  Goes through all the points in the shape and rotates them on y axis.
   @param angle the rotation angle on y axis
   */
   def rotateY(angle: Double): Unit = {
      for (point <- points) point.rotateY(angle)
   }
   /**
   Rotate the Shape3D on z axis by an angle.  Goes through all the points in the shape and rotates them on z axis.
   @param angle the rotation angle on z axis
   */
   def rotateZ(angle: Double): Unit = {
      for (point <- points) point.rotateZ(angle)
   }
   /**
   This creates a deep clone of the Shape3D.
   @return Shape3D (independent copy)
   */
   override def clone(): Shape3D = {
      val copy: Array[Vector3D] = new Array[Vector3D](pointsTotal)
      var i: Int = 0
      for (point <- points) { copy(i) = point.clone(); i += 1 }

      val copyPolys: Array[Polygon3D] = new Array[Polygon3D](polysTotal)
      i = 0
      for (poly <- polys) { copyPolys(i) = poly.clone(copy, vertexOrders(i)); i += 1 }

      new Shape3D(copy.toList, copyPolys.toList)
   }
   /**
   Private function for building list of point references for each polygon
   */
   private def makeVertexOrders(): Unit = {
      var polyCount: Int = 0
      for (poly <- polys) {
         var pointCount: Int = 0
         val orders: Array[Int] = new Array[Int](poly.points.size)
         for (point <- poly.points) {
            orders(pointCount) = getVertexIndex(point)
            pointCount += 1
         }
         vertexOrders(polyCount) = orders
         polyCount += 1
      }
      //printVertexOrders()
   }
   /**
   Private function for getting the point index from a polygon point reference
   */
   private def getVertexIndex(vertexRef: Vector3D): Int = {
      var index: Int = -1//error code
      for (i <- 0 until points.size) {
         if (points(i) == vertexRef) {
            index = i
         }
      }
      index
   }
   /**
   Prints out the array of point indexes for each polygon
   */
   def printVertexOrders(): Unit = {
      println("")
      println("******************************")
      println("Shape3D: the array of vertex indices for each of the shape's polygons")
      for (vertexOrder <- vertexOrders) {
         var info: String = "poly: "
         for (index <- vertexOrder) {
            info = info + index + ", "
         }
         println(info)
      }
      println("******************************")
      println("")
   }
   /**
   Set the dimensions of the shape: the maximum width, height, depth
   */
   def setDimensions(): Unit = {
      var minW: Double = 100000000
      var minH: Double = 100000000
      var minD: Double = 100000000
      var maxW: Double = -100000000
      var maxH: Double = -100000000
      var maxD: Double = -100000000
      var w: Double = 0
      var h: Double = 0
      var d: Double = 0
      for (point <- points) {
         if (point.x < minW) minW = point.x
         if (point.x > maxW) maxW = point.x
         if (point.y < minH) minH = point.y
         if (point.y > maxH) maxH = point.y
         if (point.z < minD) minD = point.z
         if (point.z > maxD) maxD = point.z
      }
      w = maxW-minW
      h = maxH-minH
      d = maxD-minD
      dimensions = new Vector3D(w, h, d)
   }

}

