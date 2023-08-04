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
            viewModel.gyroX.value = event.values[0]
            viewModel.gyroY.value = event.values[1]
            viewModel.gyroZ.value  = event.values[2]
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
}