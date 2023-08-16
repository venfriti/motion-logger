package com.example.motionlogger.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.motionlogger.BuildConfig
import com.example.motionlogger.NetworkResult
import com.example.motionlogger.R
import com.example.motionlogger.Sensors
import com.example.motionlogger.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy { MainViewModel() }
    private lateinit var binding: FragmentMainBinding
    private lateinit var sensors: Sensors

    private lateinit var inputEditText: EditText
    private lateinit var sendButton: Button

    private var sending = false
    private val sendInterval = 250L

    private var a = "0.0000"
    private var b = ""
    private var c = ""
    private var d = ""
    private var e = ""
    private var f = ""
    private var g = ""
    private var h = ""


    private lateinit var urlString: String
    private lateinit var urlWithParameter: String

    private lateinit var locationManager: LocationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        sensors = Sensors(requireContext(), viewModel)

        inputEditText = binding.editText
        sendButton = binding.button

        locationManager = requireContext()
            .getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    startLocationUpdates()
                } else {
                    Snackbar.make(
                        requireView(),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_LONG
                    )
                        .setAction(R.string.settings) {
                            startActivity(Intent().apply {
                                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                data = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
                                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            })
                        }.show()
                }
            }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            startLocationUpdates()
        }

        if (hasGyroscopeSensor()){
            viewModel.rotX.observe(viewLifecycleOwner) {
                a = String.format("%.2f", it)
                binding.gyroscopeXText.text = a
            }

            viewModel.rotY.observe(viewLifecycleOwner) {
                b = String.format("%.2f", it)
                binding.gyroscopeYText.text = b
            }

            viewModel.rotZ.observe(viewLifecycleOwner) {
                c = String.format("%.2f", it)
                binding.gyroscopeZText.text = c
            }

        } else {
            a = "0.0000"
            b = "0.0000"
            c = "0.0000"

            binding.gyroscopeXText.text = a
            binding.gyroscopeYText.text = b
            binding.gyroscopeZText.text = c

            Toast.makeText(requireContext(), "The device does not have a gyroscope", Toast.LENGTH_LONG).show()
        }

        viewModel.accelX.observe(viewLifecycleOwner) {
            d = String.format("%.4f", it)
            binding.accelerometerXText.text = d
        }

        viewModel.accelY.observe(viewLifecycleOwner) {
            e = String.format("%.4f", it)
            binding.accelerometerYText.text = e
        }

        viewModel.accelZ.observe(viewLifecycleOwner) {
            f = String.format("%.4f", it)
            binding.accelerometerZAxis.text = f
        }

        sendButton.setOnClickListener {
            if (!sending) {
                sending = true
                hideSoftKeyboard()
                urlString = inputEditText.text.toString()
                sendButton.text = getString(R.string.stop)
                urlWithParameter = "$urlString?a=$a&b=$b&c=$c&d=$d&e=$e&f=$f&g=$g&h=$h"
                startSendingData()
            } else {
                sending = false
                sendButton.text = getString(R.string.start)
            }
        }
    }

    private fun startSendingData() {
        CoroutineScope(Dispatchers.Main).launch {
            while (sending) {
                when (val result = viewModel.fetchData(urlWithParameter)) {
                    is NetworkResult.Success -> {
                        urlWithParameter = "$urlString?a=$a&b=$b&c=$c&d=$d&e=$e&f=$f&g=$g&h=$h"
                        delay(sendInterval)
                    }

                    is NetworkResult.Error -> {
                        sending = false
                        showToast(result.message)
                        sendButton.text = getString(R.string.start)
                    }
                }

            }
        }
    }

    private fun startLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                500L,  // minimum time interval between updates in milliseconds
                0f,    // minimum distance between updates in meters
                locationListener
            )
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            val latitude = location.latitude
            val longitude = location.longitude

            // Update UI with latitude and longitude
            g = String.format("%.6f", longitude)
            h = String.format("%.6f", latitude)


            binding.longitudeText.text = g
            binding.latitudeText.text = h
        }

        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun hasGyroscopeSensor(): Boolean {
        val sensorManager = requireActivity()
            .getSystemService(Context.SENSOR_SERVICE) as SensorManager

        val gyroscopeSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)

        return gyroscopeSensor != null
    }

    private fun hideSoftKeyboard() {
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.button.windowToken, 0)
    }

    private fun showToast(message: String) {
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