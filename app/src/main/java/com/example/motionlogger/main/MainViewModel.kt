package com.example.motionlogger.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.motionlogger.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainViewModel : ViewModel(){
    var accelX = MutableLiveData<Float>()
    var accelY = MutableLiveData<Float>()
    var accelZ = MutableLiveData<Float>()

    var rotX = MutableLiveData<Float>()
    var rotY = MutableLiveData<Float>()
    var rotZ = MutableLiveData<Float>()

    suspend fun fetchData(url: String): NetworkResult {
        return withContext(Dispatchers.IO) {
            try {
                val urlConnection = URL(url).openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 5000
                urlConnection.readTimeout = 5000

                val inputStream = urlConnection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }

                reader.close()
                urlConnection.disconnect()

                val responseData = response.toString()
                Log.d("Server Response", responseData)
                NetworkResult.Success(responseData)
            } catch (e: Exception) {
                e.printStackTrace()
                NetworkResult.Error("Invalid Link: $e")
            }
        }
    }

}