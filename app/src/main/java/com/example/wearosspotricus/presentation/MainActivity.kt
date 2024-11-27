package com.example.wearosspotricus.presentation

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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
        // Variables con datos biométricos de ejemplo
        var heartRate by remember { mutableStateOf(72) }
        var steps by remember { mutableStateOf(5000) }
        var distance by remember { mutableStateOf(2.5f) }

        // Aplicar tema con fondo negro
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black), // Fondo negro
            color = Color.Black
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Título de la sección "Frecuencia cardíaca"
                Text(
                    text = "Frecuencia cardíaca",
                    color = Color.White, // Texto blanco
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$heartRate bpm",
                    color = Color.White, // Texto blanco
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Título de la sección "Pasos dados"
                Text(
                    text = "Pasos dados",
                    color = Color.White, // Texto blanco
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "$steps pasos",
                    color = Color.White, // Texto blanco
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Título de la sección "Distancia recorrida"
                Text(
                    text = "Distancia recorrida",
                    color = Color.White, // Texto blanco
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = String.format("%.2f km", distance),
                    color = Color.White, // Texto blanco
                    fontSize = 36.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Botón para enviar datos
                Button(
                    onClick = {
                        sendDataToPhone(heartRate, steps, distance)
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray)
                ) {
                    Text("Enviar datos", color = Color.White)
                }
            }
        }
    }

    // Función para enviar datos al teléfono
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