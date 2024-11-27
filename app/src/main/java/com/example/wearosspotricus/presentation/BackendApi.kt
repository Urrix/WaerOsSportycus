package com.example.wearosspotricus.presentation

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface BackendApi {
    @POST("api/biometric-data") // Cambia esta ruta si es diferente en tu backend
    fun sendBiometricData(@Body data: BiometricDataRequest): Call<Void>
}

data class BiometricDataRequest(
    val user_id: Int,           // ID del usuario, asegúrate de incluirlo si el backend lo requiere
    val heart_frequency: Int,   // Frecuencia cardíaca
    val steps: Int,             // Pasos
    val distance: Float,        // Distancia recorrida
    val spO2: Int               // Saturación de oxígeno
)