#!/bin/bash
cd bin/org/loom/geometry
rm ShapeCreator.class
cd ../../../../
scalac -deprecation -d bin -sourcepath src -classpath .:lib/RXTXcomm.jar -Djava.library.path=lib src/org/loom/geometry/ShapeCreator.scala
