/**
UTILITY TIMER CLASS: General Timer class 
Start the timer and provide a time limit
Then check for times up.
You can also reset the timer
and get the elapsed time
*/

package org.loom.utility

class Timer() {
    
    var stopped: Boolean = true
    var startTime: Long = 0
    var delay: Long = 0
    /**
    Start the timer that runs for a given duration.
    @param d the duration
    */
    def startTimer(d: Int): Unit = {
        if (stopped) {
            delay = d
            setStartTime()
            stopped = false
        }
    }  
    
    def setStartTime(): Unit = startTime = System.currentTimeMillis()
    /**
    Stop the timer
    */
    def stopTimer(): Unit = stopped = true
    /**
    Get the elapsed time on the timer
    */
    def getElapsedTime(): Long = {
        if (!stopped) (System.currentTimeMillis() - startTime) else 0
    }  
    /**
    Reset the timer
    */
    def resetTimer(): Unit = {
        stopTimer()
        setStartTime()
        stopped = false
    }
    /**
    Check if times up
    @return if stopped or not (if times up then stopped)
    */
    def timesUp(): Boolean = {
        if (!stopped) {
            if (System.currentTimeMillis() > startTime + delay) {
                stopped = true
            }
        }
        stopped
    }
    
}
