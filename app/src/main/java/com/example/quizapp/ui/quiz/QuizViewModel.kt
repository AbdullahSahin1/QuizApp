package com.example.quizapp.ui.quiz

import android.text.Html
import android.util.Base64
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.nio.charset.StandardCharsets

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswer: String
)

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val score: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null
)

class QuizViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        Log.d("QuizViewModel", "QuizViewModel initialized")
        loadQuestions()
    }

    private fun decodeBase64(encodedString: String): String {
        return try {
            val decodedBytes = Base64.decode(encodedString, Base64.DEFAULT)
            String(decodedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            Log.e("QuizViewModel", "Base64 decode hatası", e)
            encodedString
        }
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                Log.d("QuizViewModel", "API çağrısı başlatılıyor...")
                
                val response = RetrofitClient.quizApiService.getQuestions(amount = 10)
                Log.d("QuizViewModel", "API yanıtı alındı: ${response.results.size} soru")
                Log.d("QuizViewModel", "API yanıt detayı: $response")
                
                if (response.results.isEmpty()) {
                    Log.e("QuizViewModel", "API boş sonuç döndürdü")
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Sorular yüklenemedi. Lütfen internet bağlantınızı kontrol edin."
                    )
                    return@launch
                }
                
                val questions = response.results.map { apiQuestion ->
                    QuizQuestion(
                        question = Html.fromHtml(apiQuestion.question, Html.FROM_HTML_MODE_COMPACT).toString(),
                        options = (apiQuestion.incorrect_answers + apiQuestion.correct_answer).shuffled()
                            .map { Html.fromHtml(it, Html.FROM_HTML_MODE_COMPACT).toString() },
                        correctAnswer = Html.fromHtml(apiQuestion.correct_answer, Html.FROM_HTML_MODE_COMPACT).toString()
                    )
                }
                
                _uiState.value = _uiState.value.copy(
                    questions = questions,
                    isLoading = false
                )
                Log.d("QuizViewModel", "Sorular başarıyla yüklendi: ${questions.size} soru")
            } catch (e: Exception) {
                Log.e("QuizViewModel", "Hata oluştu", e)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Sorular yüklenirken bir hata oluştu: ${e.message}"
                )
            }
        }
    }

    fun answerQuestion(selectedAnswer: String) {
        val currentState = _uiState.value
        val currentQuestion = currentState.questions.getOrNull(currentState.currentQuestionIndex) ?: return

        val newScore = if (selectedAnswer == currentQuestion.correctAnswer) {
            currentState.score + 1
        } else {
            currentState.score
        }

        val newQuestionIndex = currentState.currentQuestionIndex + 1
        _uiState.value = currentState.copy(
            currentQuestionIndex = newQuestionIndex,
            score = newScore
        )
    }

    fun resetQuiz() {
        loadQuestions()
    }
} 