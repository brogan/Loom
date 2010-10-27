#!/bin/bash
sketch=`grep -v "xml" sketch.txt`
config=`grep "xml" sketch.txt`
java -d32 -Djava.library.path=lib -jar $sketch.jar $sketch $config
