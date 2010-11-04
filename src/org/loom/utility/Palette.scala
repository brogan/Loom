/**
Palette provides a means of storing a set of user defined colors in a Map.  They are then accessible
either by name or by index.  Colors can be added and removed from the map.
*/

package org.loom.utility

import java.awt.Color
import scala.collection.mutable.Map

class Palette() {

   val palette: Map[String, Color] = Map[String, Color]()
   /**
   Adds a user defined color to the palette.
   @param name the user defined name for the color
   @param color the java.awt.Color to store
   */
   def addColor(name: String, color: Color): Unit = {
      palette += (name -> color)
   }
   /**
   Removes a user defined color from the palette.
   @param name the name of the color to remove
   */
   def removeColor(name: String): Unit = {
      palette -= name
   }
   /**
   Gets a user defined color from the palette by name if that color is available,
   otherwise prints out that the color is not available (error String).
   @param name the name of the color to get
   @return either a color or the error String
   */
   def getColor(name: String): Any = {
      palette.getOrElse(name, "Color not available in palette")
   }
   /**
   Gets a user defined color from the palette by index.
   Take care to specify an appropriate index.
   @param index the index of the color in the palette
   @return the color at the specified index
   */
   def getColorByIndex(index: Int): Color = {
      val keys: Array[String] = getKeys()
      val key: String = keys(index)
      palette(key)
   }
   /**
   Gets the array of color names in the palette.
   @return array of color names
   */
   def getKeys(): Array[String] = {
      palette.keySet.toArray[String]
   }
   /**
   Gets the number of colors in the palette.
   @return the number of colors in the palette
   */
   def getSize(): Int = {
      palette.size
   }

}

