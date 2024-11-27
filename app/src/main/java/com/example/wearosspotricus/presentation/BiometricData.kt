package com.example.wearosspotricus.presentation

data class BiometricData(
    val heart_frequency: Int,
    val pressure: Int,
    val calories: Int,
    val sleep_quality: Float,
    val sleep_minutes: Int,
    val steps: Int,
    val spO2: Int // Añadir aquí
)