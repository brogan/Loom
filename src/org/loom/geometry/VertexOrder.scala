/**
VertexOrder - used internally in Shape3D to keep a record of the point (vertices) that
individual polygons in the shape reference.  This is necessary so that a properly cloned
copy of the shape can be created.  When you clone a shape we begin by cloning its list of 
points, then we need to clone the polygons and update their references so that they refer to
the relevant new points.  We can only do this if we keep a record of which points they reference, specifically their
index position in the list of points.  Note: there is no need to instantiate this class yourself.
*/

package org.loom.geometry

class VertexOrder(val vertices: Array[Int])
