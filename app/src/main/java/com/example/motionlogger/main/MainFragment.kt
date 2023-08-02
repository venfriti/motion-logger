package com.example.motionlogger.main

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.motionlogger.R
import com.example.motionlogger.databinding.FragmentMainBinding

//import com.example.motionlogger.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy { MainViewModel() }
    private lateinit var binding: FragmentMainBinding

//    private lateinit var gyroscopeAndAccelerometerSensor: GyroscopeAndAccelerometerSensor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

//        if (hasGyroscopeSensor()){
//            viewModel.gyroX.observe(viewLifecycleOwner) {
//                binding.gyroscopeXText.text = String.format("%.4f", it)
//            }
//
//            viewModel.gyroY.observe(viewLifecycleOwner) {
//                binding.gyroscopeYText.text = String.format("%.4f", it)
//            }
//
//            viewModel.gyroZ.observe(viewLifecycleOwner) {
//                binding.gyroscopeZText.text = String.format("%.4f", it)
//            }
//
//        } else {
//            binding.gyroscopeXText.text = "0.0"
//            binding.gyroscopeYText.text = "0.0"
//            binding.gyroscopeZText.text = "0.0"
//
//            Toast.makeText(requireContext(), "The device does not have a gyroscope", Toast.LENGTH_LONG)
//                .show()
//        }
//
//        binding.enterButton.setOnClickListener{
//            hideSoftKeyboard()
//            binding.enterButton.visibility = View.GONE
//            binding.editButton.visibility = View.VISIBLE
//            binding.customUrl.visibility = View.GONE
//            binding.customText.text = binding.customText.text
//            binding.customText.visibility = View.VISIBLE
//        }
//
//        binding.editButton.setOnClickListener{
//            binding.enterButton.visibility = View.VISIBLE
//            binding.editButton.visibility = View.GONE
//            binding.customUrl.visibility = View.VISIBLE
//            binding.customText.visibility = View.GONE
//        }
//
//
//        viewModel.accelX.observe(viewLifecycleOwner) {
//            binding.accelerometerXText.text = String.format("%.4f", it)
//        }
//
//        viewModel.accelY.observe(viewLifecycleOwner) {
//            binding.accelerometerYText.text = String.format("%.4f", it)
//        }
//
//        viewModel.accelZ.observe(viewLifecycleOwner) {
//            binding.accelerometerZAxis.text = String.format("%.4f", it)
//        }
//
//        binding.viewModel = viewModel
//
//        gyroscopeAndAccelerometerSensor = GyroscopeAndAccelerometerSensor(requireContext(), viewModel)

        return binding.root
    }

//    private fun hideSoftKeyboard() {
//        val imm =
//            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(binding.enterButton.windowToken, 0)
//    }
//
//    private fun hasGyroscopeSensor(): Boolean {
//        val sensorManager = requireActivity()
//            .getSystemService(Context.SENSOR_SERVICE) as SensorManager
//
//        val gyroscopeSensor : Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
//
//        return gyroscopeSensor != null
//    }
//
//    override fun onResume() {
//        super.onResume()
//        gyroscopeAndAccelerometerSensor.startListening()
//    }
//
//    override fun onPause() {
//        super.onPause()
//        gyroscopeAndAccelerometerSensor.stopListening()
//    }
}