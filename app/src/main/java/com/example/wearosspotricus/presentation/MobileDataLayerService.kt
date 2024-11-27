package com.example.wearosspotricus.presentation

import android.util.Log
import com.google.android.gms.wearable.WearableListenerService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileDataLayerService : WearableListenerService() {

    override fun onDataChanged(dataEvents: com.google.android.gms.wearable.DataEventBuffer) {
        val heartRate = 75       // Sustituye estos valores por los datos reales
        val steps = 1000         // Sustituye estos valores por los datos reales
        val distance = 2.5f      // Sustituye estos valores por los datos reales
        val spO2 = 98            // Sustituye estos valores por los datos reales
        val userId = 1           // ID del usuario (modifica según sea necesario)

        val api = RetrofitInstance.getRetrofitInstance().create(BackendApi::class.java)

        val biometricData = BiometricDataRequest(
            user_id = userId,
            heart_frequency = heartRate,
            steps = steps,
            distance = distance,
            spO2 = spO2
        )

        api.sendBiometricData(biometricData).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("MobileDataLayerService", "Data sent successfully")
                } else {
                    Log.e("MobileDataLayerService", "Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("MobileDataLayerService", "Failure: ${t.message}")
            }
        })
    }
}