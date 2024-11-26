package com.example.wearosspotricus.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.google.android.gms.wearable.*
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WearApp()
        }
    }

    @Composable
    fun WearApp() {
        var heartRate by remember { mutableStateOf(72) }
        var steps by remember { mutableStateOf(5000) }
        var distance by remember { mutableStateOf(2.5f) }

        Scaffold(
            timeText = { TimeText() },
            vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = "Frecuencia cardíaca",
                    style = MaterialTheme.typography.title3,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$heartRate bpm",
                    style = MaterialTheme.typography.display1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pasos dados",
                    style = MaterialTheme.typography.title3,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$steps pasos",
                    style = MaterialTheme.typography.display1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Distancia recorrida",
                    style = MaterialTheme.typography.title3,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$distance km",
                    style = MaterialTheme.typography.display1,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    sendDataToPhone(heartRate, steps, distance)
                }) {
                    Text("Enviar datos")
                }
            }
        }
    }

    private fun sendDataToPhone(heartRate: Int, steps: Int, distance: Float) {
        val dataMapRequest = PutDataMapRequest.create("/biometric_data").apply {
            dataMap.putInt("heart_rate", heartRate)
            dataMap.putInt("steps", steps)
            dataMap.putFloat("distance", distance)
        }

        val putDataRequest = dataMapRequest.asPutDataRequest()
        Wearable.getDataClient(this).putDataItem(putDataRequest).addOnSuccessListener {
            println("Datos enviados exitosamente al teléfono.")
        }.addOnFailureListener {
            println("Error al enviar datos: ${it.message}")
        }
    }
}
