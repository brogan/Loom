/**
Config - parses config files
*/
package org.loom.scaffold

import org.loom.media._
import java.awt.Color
import scala.io.Source._
import scala.xml._

object Config {

    var config: Seq[Node] = null
    var name: String = "noname"
    var width: Int = 720
    var height: Int = 720
    var animating: Boolean = true
    var fullscreen: Boolean = false
    var borderColor: Color = new Color(0,0,0)
    var serial: Boolean = false
    var port: String = "dev/ttyUSB0"
    var mode: String = "bytes"
    var quantity: Int = 1

    def configure(sketchName: String, configName: String): Unit = {
        config = XML.loadFile(ProjectFilePath.filePath + ProjectFilePath.separator + "sketches" + ProjectFilePath.separator + sketchName + ProjectFilePath.separator + "config" + ProjectFilePath.separator + configName)
        name = sketchName
        width = (config \ "width").text.toInt
        height = (config \ "height").text.toInt
        animating = (config \ "animating").text.toBoolean
        fullscreen = (config \ "fullscreen").text.toBoolean
        borderColor = getColor((config \ "borderColor").text)
        serial = (config \ "serial").text.toBoolean
        port = (config \ "port").text
        mode = (config \ "mode").text
        quantity = (config \ "quantity").text.toInt
    }

    def getColor(col: String): Color = {
        val c: Array[String] = col.split(",")
        new Color(c(0).toInt, c(1).toInt, c(2).toInt)
    }

    override def toString(): String = {
        "\nConfig:\n" +
        "   name: " + name + "\n" +
        "   width: " + width + "\n" +
        "   height: " + height + "\n" +
        "   animating: " + animating + "\n" +
        "   fullscreen: " + fullscreen + "\n" +
        "   borderColor: " + borderColor + "\n" +
        "   serial: " + serial + "\n" +
        "   port: " + port + "\n" +
        "   mode: " + mode + "\n" +
        "   quantity: " + quantity + "\n\n"
    }
}
