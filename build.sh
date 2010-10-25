#!/bin/bash
sketch=`grep -v "xml" sketch.txt`
config=`grep "xml" sketch.txt`
cd bin/org/loom/mysketch
rm *.class
cd ../../../../
scalac -deprecation -d bin -sourcepath sketches -classpath bin sketches/$sketch/MySketch.scala
