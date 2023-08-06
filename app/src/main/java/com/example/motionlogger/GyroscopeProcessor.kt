package com.example.motionlogger

class GyroscopeProcessor {
    private var previousTimestamp: Long = 0
    private var referenceAngles = floatArrayOf(0f, 0f, 0f)
    private var currentAngles = floatArrayOf(0f, 0f, 0f)

    fun processGyroscopeData(timestamp: Long, angularVelocity: FloatArray) {
        if (previousTimestamp != 0L) {
            val deltaTime = (timestamp - previousTimestamp) / 1000.0f
            for (i in 0 until 3) {
                val deltaAngle = angularVelocity[i] * deltaTime
                currentAngles[i] = referenceAngles[i] + deltaAngle
            }
        }
        previousTimestamp = timestamp
    }

    fun getCurrentAngles(): FloatArray {
        return currentAngles
    }
}
