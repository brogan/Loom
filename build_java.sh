#!/bin/bash
sketch=`grep -v "xml" sketch.txt`
config=`grep "xml" sketch.txt`
cd bin
jar -cfm ../$sketch.jar ../MANIFEST.MF *
cd ../
java -Djava.library.path=lib -jar $sketch.jar $sketch $config
