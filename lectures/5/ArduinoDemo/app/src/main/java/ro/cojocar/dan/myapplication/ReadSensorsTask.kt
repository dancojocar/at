package ro.cojocar.dan.myapplication

import java.util.*

class ReadSensorsTask(val arduino: Arduino) : TimerTask() {

    override fun run() {
        readHumidity()
        Thread.sleep(2000)
        readTemp()
        Thread.sleep(2000)
        readAir()
    }


    private fun readHumidity() {
        arduino.write("H")
        Thread.sleep(200)
        val humidity = arduino.read()
        logi("Humidity $humidity%")
    }

    private fun readTemp() {
        arduino.write("T")
        Thread.sleep(200)
        val output = arduino.read()
        logi("Temperature ${output}C")
    }

    private fun readAir() {
        arduino.write("A")
        Thread.sleep(200)
        val output = arduino.read()
        logi("Air: ${output} ppm")
    }

}