package com.example.motionlogger

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.example.motionlogger.main.MainViewModel

class Sensors(context: Context, private val viewModel: MainViewModel) : SensorEventListener {

    private val sensorManager: SensorManager = context
        .getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val gyroscopeSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    private val accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor == gyroscopeSensor){
            val deltaTime = (event.timestamp - viewModel.previousTimestamp) * NS2S
            viewModel.previousTimestamp = event.timestamp

            val deltaAngleX = event.values[0] * deltaTime
            val deltaAngleY = event.values[1] * deltaTime
            val deltaAngleZ = event.values[2] * deltaTime

            viewModel.orientationAngles.value?.let { currentAngles ->
                currentAngles[0] += (Math.toDegrees(deltaAngleX.toDouble())).toFloat()
                currentAngles[1] += Math.toDegrees(deltaAngleY.toDouble()).toFloat()
                currentAngles[2] += Math.toDegrees(deltaAngleZ.toDouble()).toFloat()

                viewModel.orientationAngles.value = currentAngles
            }
        }

        if (event.sensor == accelerometerSensor){
            viewModel.accelX.value = event.values[0]
            viewModel.accelY.value = event.values[1]
            viewModel.accelZ.value = event.values[2]
        }
    }

    fun startListening() {
        gyroscopeSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        val yes = accuracy
    }

    companion object {
        private const val NS2S = 1.0f / 1_000_000_000.0f // Nanoseconds to seconds
    }
}