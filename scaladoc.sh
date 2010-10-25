#!/bin/bash
scaladoc -d doc -sourcepath src -classpath .:lib/RXTXcomm.jar src/org/loom/scaffold/*.scala
