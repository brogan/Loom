#!/bin/bash
sketch=`grep -v "xml" sketch.txt`
config=`grep "xml" sketch.txt`
cd bin
rm -r org
cd ../
scalac -deprecation -d bin -sourcepath src -classpath .:lib/RXTXcomm.jar -Djava.library.path=lib src/org/loom/scaffold/*.scala
scalac -deprecation -d bin -sourcepath src -classpath .:lib/RXTXcomm.jar -Djava.library.path=lib src/org/loom/geometry/*.scala
scalac -deprecation -d bin -sourcepath src -classpath .:lib/RXTXcomm.jar -Djava.library.path=lib src/org/loom/interaction/*.scala
scalac -deprecation -d bin -sourcepath src -classpath .:lib/RXTXcomm.jar -Djava.library.path=lib src/org/loom/media/*.scala
scalac -deprecation -d bin -sourcepath src -classpath .:lib/RXTXcomm.jar -Djava.library.path=lib src/org/loom/scene/*.scala
scalac -deprecation -d bin -sourcepath src -classpath .:lib/RXTXcomm.jar -Djava.library.path=lib src/org/loom/utility/*.scala
cd bin
scala -classpath .:../lib/RXTXcomm.jar -Djava.libary.path=../lib org.loom.scaffold.Main $sketch $config
