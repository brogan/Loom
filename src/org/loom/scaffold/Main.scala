/**
Program entry point
*/

package org.loom.scaffold

object Main {
    def main(args: Array[String]): Unit  = {
        val sketchName: String = args(0)
        val configName: String = args(1)
        println("Main loaded: sketchName: " + sketchName + "  config file: " + configName)
        Config.configure(sketchName, configName)
        println(Config.toString())
        val frame: DrawFrame = new DrawFrame()

    }
}
