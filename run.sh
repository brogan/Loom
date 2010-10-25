#!/bin/bash
sketch=`grep -v "xml" sketch.txt`
config=`grep "xml" sketch.txt`
cd bin
scala -classpath .:../lib/RXTXcomm.jar -Djava.libary.path=../lib org.loom.scaffold.Main $sketch $config
