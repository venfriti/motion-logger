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

    private val accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    private val magnetometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

    private val rotationMatrix = FloatArray(9)
    private val inclinationMatrix = FloatArray(9)
    private val orientationAngles = FloatArray(3)
    private val accelAngles = FloatArray(3)
    private val magnetAngles = FloatArray(3)

    override fun onSensorChanged(event: SensorEvent) {

        if (event.sensor == accelerometerSensor){
            viewModel.accelX.value = event.values[0]
            viewModel.accelY.value = event.values[1]
            viewModel.accelZ.value = event.values[2]
            accelAngles[0] = event.values[0]
            accelAngles[1] = event.values[1]
            accelAngles[2] = event.values[2]
        }

        if (event.sensor == magnetometerSensor){
            magnetAngles[0] = event.values[0]
            magnetAngles[1] = event.values[1]
            magnetAngles[2] = event.values[2]
        }

        if (accelerometerSensor != null || magnetometerSensor != null){
            val gravity = floatArrayOf(accelAngles[0], accelAngles[1], accelAngles[2])
            val geomagnetic = floatArrayOf(magnetAngles[0], magnetAngles[1], magnetAngles[2])

            SensorManager.getRotationMatrix(rotationMatrix, inclinationMatrix, gravity, geomagnetic)
            SensorManager.getOrientation(rotationMatrix, orientationAngles)

            viewModel.rotZ.value = Math.toDegrees(orientationAngles[0].toDouble()).toFloat()
            viewModel.rotX.value = Math.toDegrees(orientationAngles[1].toDouble()).toFloat()
            viewModel.rotY.value = Math.toDegrees(orientationAngles[2].toDouble()).toFloat()

        }

    }

    fun startListening() {
        accelerometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }

        magnetometerSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        val yes = accuracy
    }
}