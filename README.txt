Loom
October 24 2010
Brogan Bunt

This is free software under the GPL license: http://www.gnu.org/licenses/gpl.html

See the tutorial section for info and instructions.  Here is some brief info on the build scripts:

All of the build files (apart from build_class.sh) get their info about the relevant sketch and configuration file from the sketch.txt file.
You need to modify the sketch.txt file when you want to work with a different sketch.  The first line is the name of the sketch.  The second
line is the configuration xml file.

build.sh: compiles just the MySketch file
build_all.sh: compiles everything in the src folder (but not the external MySketch) - very slow!
build_class.sh: convenience - compile just one class - need to adjust sh file to point to your class and package
build_java.sh: creates jar file for the sketch
run.sh: runs the sketch
run_java.sh: runs the sketch jar file (if created)
run_java_mac.sh: runs the sketch jar file (if created) on OSX 64bit machines (see Loom.pdf in tutorials)
