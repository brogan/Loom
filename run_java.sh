#!/bin/bash
sketch=`grep -v "xml" sketch.txt`
config=`grep "xml" sketch.txt`
java -Djava.library.path=lib -jar $sketch.jar $sketch $config
