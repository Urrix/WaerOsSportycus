package com.example.wearosspotricus.presentation

data class BiometricData(
    val user_id: Int,
    val heart_frequency: Int,
    val pressure: Int,
    val calories: Int,
    val sleep_quality: Float,
    val sleep_minutes: Int,
    val steps: Int
)
