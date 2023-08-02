package com.example.motionlogger.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel(){
    // TODO: Implement the ViewModel
    var gyroX = MutableLiveData<Float>()
    var gyroY = MutableLiveData<Float>()
    var gyroZ = MutableLiveData<Float>()

    var accelX = MutableLiveData<Float>()
    var accelY = MutableLiveData<Float>()
    var accelZ = MutableLiveData<Float>()

}