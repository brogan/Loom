/**
SoundManager
Holds a set of up to 8 sound files to play.
SoundManager provides the interface for controlling individual sounds.
Maximum of 8 simultaneous sounds (for now).
*/

package org.loom.media

import javax.sound.sampled._
import java.io._

class SoundManager() {

   var sounds: Array[SoundPlay] = new Array[SoundPlay](8)//maximum of 8 sounds
   /**
   addSound
   @param pathy path to sound file
   @param looping are we looping? (Boolean)
   @param loopCount how many times to loop
   @param index where to add the sound in the array of sounds
   */
   def addSound(pathy: String, looping: Boolean, loopCount: Int, index: Int) {
      println("adding sound: " + pathy)
      sounds(index) = new SoundPlay(pathy, looping, loopCount)
   }
   /**
   stopSound
   @param index in sounds array
   */
   def stopSound(index: Int) {
      sounds(index).stopSound()
      sounds(index) = null;
   }
   /**
   stopSounds
   */  
   def stopSounds() {
      for (sound <- sounds) {
         sound.stopSound()
	 //sound = null;
      }
   }
   /**
   setVolume
   @param index in sounds array
   @param percent the percentage to increase or decrease gain
   */
   def setVolume(index: Int, percent: Float) {
      sounds(index).setGain(percent)
   }

}
