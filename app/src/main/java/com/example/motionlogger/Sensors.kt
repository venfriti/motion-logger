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
    private val rotationSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

    private val rotationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)

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

        if (event.sensor == rotationSensor){
            val rotationVector = event.values

            SensorManager.getRotationMatrixFromVector(rotationMatrix, rotationVector)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)

            viewModel.rotX.value = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            viewModel.rotY.value = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
            viewModel.rotZ.value = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()

        }

    }

    fun startListening() {
        gyroscopeSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        rotationSensor?.let {
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