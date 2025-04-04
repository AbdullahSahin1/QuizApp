package com.example.quizapp.api

import android.util.Log
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApiService {
    @GET("https://opentdb.com/api.php?amount=50")
    suspend fun getQuestions(
        @Query("amount") amount: Int = 50,
        @Query("type") type: String = "multiple"
    ): QuizResponse {
        Log.d("QuizApiService", "getQuestions çağrıldı: amount=$amount, type=$type")
        return this.getQuestions(amount, type)
    }
}

data class QuizResponse(
    val response_code: Int,
    val results: List<QuizApiQuestion>
)

data class QuizApiQuestion(
    val category: String,
    val type: String,
    val difficulty: String,
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>
) 