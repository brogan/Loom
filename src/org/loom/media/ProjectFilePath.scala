/**
Holds project file path and system dependent file separator
*/

package org.loom.media

import java.io.File
import org.loom.scaffold._

object ProjectFilePath {

   val IMAGE: String = "images"
   val SEQUENCE: String = "sequences"
   val SOUND: String = "sound"

   val separator: String = File.separator//the platform dependent file separator

   var filePath: String = System.getProperty("user.dir")
   if (filePath.contains(separator + "bin")) {
      val end: Int = filePath.indexOf(separator + "bin")
      filePath = filePath.substring(0, end)
   }

   def getResourceFilePath(resourceType: String, name: String): String = {
      var resourcePath: String = ""
      resourceType match {
         case IMAGE => resourcePath = filePath + separator + "sketches" + separator + Config.name + separator + "resources" + separator + IMAGE + separator + name
         case SEQUENCE => resourcePath = filePath + separator + "sketches" + separator + Config.name + separator + "resources" + separator + SEQUENCE + separator + name
         case SOUND => resourcePath = filePath + separator + "sketches" + separator + Config.name + separator + "resources" + separator + SOUND + separator + name
      }
      resourcePath
   }

}
