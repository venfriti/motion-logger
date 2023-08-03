package com.example.motionlogger.main

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.motionlogger.NetworkResult
import com.example.motionlogger.R
import com.example.motionlogger.Sensors
import com.example.motionlogger.databinding.FragmentMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy { MainViewModel() }
    private lateinit var binding: FragmentMainBinding
    private lateinit var sensors: Sensors

    private lateinit var inputEditText: EditText
    private lateinit var sendButton: Button

    private var sending = false
    private val sendInterval = 500L

    private lateinit var a : String
//    private lateinit var b : String

    private var b = ""

    private lateinit var urlString: String
    private lateinit var urlWithParameter: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        sensors = Sensors(requireContext(), viewModel)


//        if (hasGyroscopeSensor()){
//            viewModel.gyroX.observe(viewLifecycleOwner) {
//                a = String.format("%.4f", it)
//            }
//
//            viewModel.gyroY.observe(viewLifecycleOwner) {
//                b = String.format("%.4f", it)
//            }
//
//            viewModel.gyroZ.observe(viewLifecycleOwner) {
//                binding.gyroscopeZText.text = String.format("%.4f", it)
//            }

//        } else {
//            binding.gyroscopeXText.text = "0.0"
//            binding.gyroscopeYText.text = "0.0"
//            binding.gyroscopeZText.text = "0.0"
//
//            Toast.makeText(requireContext(), "The device does not have a gyroscope", Toast.LENGTH_LONG)
//                .show()
//        }

        inputEditText = binding.editText
        sendButton = binding.button

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.accelX.observe(viewLifecycleOwner) {
            a = String.format("%.4f", it)
            binding.accelX.text = String.format("%.4f", it)
        }

        viewModel.accelY.observe(viewLifecycleOwner) {
            b = String.format("%.4f", it)
            binding.accelY.text = String.format("%.4f", it)
        }

        sendButton.setOnClickListener {
            if (!sending){
                sending = true
                hideSoftKeyboard()
                urlString = inputEditText.text.toString()
                sendButton.text = getString(R.string.stop)
                urlWithParameter = "$urlString?a=$a&b=$b"
                startSendingData(urlWithParameter)
            } else {
                sending = false
                sendButton.text = getString(R.string.start)
            }

        }

    }

    private fun startSendingData(url: String) {
        CoroutineScope(Dispatchers.Main).launch {
            while (sending) {
                when (val result = viewModel.fetchData(url)) {
                    is NetworkResult.Success -> {
                        delay(sendInterval)
                    }
                    is NetworkResult.Error ->{
                        sending = false
                        showToast(result.message)
                        sendButton.text = getString(R.string.start)
                    }
                }

            }
        }
    }

    private fun hasGyroscopeSensor(): Boolean {
        val sensorManager = requireActivity()
            .getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val gyroscopeSensor : Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        return gyroscopeSensor != null
    }

    private fun hideSoftKeyboard() {
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.button.windowToken, 0)
    }

    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        sensors.startListening()
    }

    override fun onPause() {
        super.onPause()
        sensors.stopListening()
    }


}