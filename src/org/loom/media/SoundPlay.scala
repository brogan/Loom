/**
SoundPlay - works in conjunction with SoundManager
 */

package org.loom.media

import javax.sound.sampled._
import java.io._

class SoundPlay(var soundFile: String, var looping: Boolean, var loopCount: Int) extends LineListener with Runnable {
	
   var clip: Clip = null
   var gainControl: FloatControl = null
   var panControl: FloatControl = null
   val audioThread: Thread = new Thread(this)
   var isRunning: Boolean = true
   audioThread.start()
   var gainMin: Float = 0
   var gainMax: Float = 0

   def run(): Unit = {
      loadClip()
      play()
      try {
         Thread.sleep(600000);
      } catch {
	 case ex: InterruptedException => println("Sleep interrupted");
      }
   }
   /**
   Play sound
   */
   def play(): Unit = {
      if (!looping) {
         if (clip!= null) {
	    clip.start()
	 }
      } else {
	 if (clip!= null) {
	    clip.loop(loopCount) 
	 }
      }
   }
   /**
   Stop sound
   */
   def stopSound(): Unit = {
      clip.stop();
      clip.setFramePosition(0)
   }

   def update(lineEvent: LineEvent): Unit = {
      if (lineEvent.getType()==LineEvent.Type.STOP) {
         clip.stop()
	 clip.setFramePosition(0)
	 lineEvent.getLine().close()
      }
   }
   /**
   Set the gain value (volume)
   of the sound.
   @param percent the percentage to increase or decrease gain
   */
   def setGain(percent: Float): Unit = {
      if (gainControl != null) {
         val inc = (math.abs(gainMin) + math.abs(gainMax))/100//get total range divided by 100
         val gain: Float = (percent * inc)+gainMin
         //println("gain: " + gain)
         gainControl.setValue(gain)
      } else {
         println("gain control = null")
      }
   }
   /**
   Set the pan value of mono sound.
   @param percent the percentage to position pan (0 full left, 100 full right)
   */
   def setPan(percent: Float): Unit = {
      if (panControl != null) {
         val pan = (percent/50)-1
         panControl.setValue(pan)
      } else {
         println("pan control = null")
      }
   }

   def loadClip() {
      try {
         var stream: AudioInputStream = AudioSystem.getAudioInputStream(new File(soundFile))
	 //the following from Davison does not work!!!!
	 //AudioInputStream stream = AudioSystem.getAudioInputStream(getClass().getResource(soundFile))
         var format: AudioFormat = stream.getFormat()
	 println("Audio format: " + format)
	 if ((format.getEncoding()==AudioFormat.Encoding.ULAW) || (format.getEncoding()==AudioFormat.Encoding.ALAW)) {
	    println("Trying to deal with ULAW/ALAW")
	    val newFormat: AudioFormat  = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
		format.getSampleRate(),
		format.getSampleSizeInBits()*2,
		format.getChannels(),
		format.getFrameSize()*2,
		format.getFrameRate(), true)
	    stream = AudioSystem.getAudioInputStream(newFormat, stream)
	    println("Converted audio format: " + newFormat)
	    format = newFormat
	  }

	  val info: DataLine.Info = new DataLine.Info(classOf[Clip], format)
	  if (!AudioSystem.isLineSupported(info)) {
	     System.out.println("Unsupported clip file: "+ soundFile)
	  }
	  clip = AudioSystem.getLine(info).asInstanceOf[Clip]
	  clip.addLineListener(this)
	  clip.open(stream)
          val controls: Array[Control] = clip.getControls()
          for (control <- controls) println ("control type: " + control.getType())

          gainControl = clip.getControl(FloatControl.Type.MASTER_GAIN).asInstanceOf[FloatControl]
          gainMin = gainControl.getMinimum()
          gainMax = gainControl.getMaximum()
          panControl = clip.getControl(FloatControl.Type.PAN).asInstanceOf[FloatControl]
          setPan(50)//middle
	  val duration: Double = clip.getMicrosecondLength()/1000000.0 
	  println("Duration: "+ duration)
			
      } catch {
          case audioException: UnsupportedAudioFileException => println("No audio line available for: "+ soundFile)
          case noLineException: LineUnavailableException => println("No audio line available for: "+ soundFile)
          case ioException: IOException => println("Could not read: "+ soundFile)
          case e: Exception => println("Problem with: "+ soundFile)

      }
   }

}
