package com.example.motionlogger


import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.ComponentActivity
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            PlantDetailDescription()
        }
    }
}

@Composable
fun PlantDetailDescription() {
    Surface {
        Text("Hello Compose")
    }
}