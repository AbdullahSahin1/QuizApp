package com.example.quizapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "https://opentdb.com/api.php?amount=50"
    private const val TAG = "RetrofitClient"

    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d(TAG, "API Response: $message")
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Log.d(TAG, "Retrofit instance oluşturuluyor...")
        Log.d(TAG, "BASE_URL: $BASE_URL")
        
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .also {
                Log.d(TAG, "Retrofit instance başarıyla oluşturuldu")
            }
    }

    val quizApiService: QuizApiService by lazy {
        Log.d(TAG, "QuizApiService oluşturuluyor...")
        retrofit.create(QuizApiService::class.java).also {
            Log.d(TAG, "QuizApiService başarıyla oluşturuldu")
        }
    }
    
    init {
        Log.d(TAG, "RetrofitClient initialized")
        Log.d(TAG, "BASE_URL: $BASE_URL")
        Log.d(TAG, "QuizApiService: $quizApiService")
    }
} 