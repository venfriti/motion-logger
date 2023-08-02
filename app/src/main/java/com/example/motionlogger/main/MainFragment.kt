package com.example.motionlogger.main

import android.content.Context
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

    private lateinit var sendButton: Button

    private var sending = false
    private val sendInterval = 500L

    private val a = 'f'
    private val b = 'g'

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false
        )

        val inputEditText = binding.editText
        sendButton = binding.button


        sendButton.setOnClickListener {
            if (!sending){
                sending = true
                hideSoftKeyboard()
                val urlString = inputEditText.text.toString()
                sendButton.text = getString(R.string.stop)
                val urlWithParameter = "$urlString?a=$a&b=$b"
                startSendingData(urlWithParameter)
            } else {
                sending = false
                sendButton.text = getString(R.string.start)
            }

        }

        return binding.root
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


    private fun hideSoftKeyboard() {
        val imm =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.button.windowToken, 0)
    }

    private fun showToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }


}