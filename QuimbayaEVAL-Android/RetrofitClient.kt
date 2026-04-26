package com.sena.quimbayaeval.data.remote.api

import com.sena.quimbayaeval.BuildConfig
import com.sena.quimbayaeval.data.local.sharedpref.PreferencesManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RetrofitClient @Inject constructor(
    private val preferencesManager: PreferencesManager
) {

    fun getApiService(): QuimbayaEVALService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuimbayaEVALService::class.java)
    }

    private fun getOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(getLoggingInterceptor())
            .addInterceptor { chain ->
                // Agregar token JWT a cada request
                val token = preferencesManager.getAuthToken()
                val request = chain.request().newBuilder()
                
                if (!token.isNullOrEmpty()) {
                    request.addHeader("Authorization", "Bearer $token")
                }
                
                request.addHeader("Content-Type", "application/json")
                chain.proceed(request.build())
            }
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return loggingInterceptor
    }
}
