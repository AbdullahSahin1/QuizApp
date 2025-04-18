package com.example.quizapp.ui.quiz

import android.text.Html
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    private fun loadQuestions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                Log.d("QuizViewModel", "API çağrısı başlatılıyor...")
                Log.d("QuizViewModel", "RetrofitClient durumu: ${RetrofitClient.quizApiService}")
                Log.d("QuizViewModel", "API URL: https://opentdb.com/api.php?amount=50&type=multiple")
                
                // API'den 50 adet çoktan seçmeli soru al
                val response = RetrofitClient.quizApiService.getQuestions(
                    amount = 50,  // 50 soru iste
                    type = "multiple"  // Çoktan seçmeli soru tipi
                )
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
                    Log.d("QuizViewModel", "Soru işleniyor: ${apiQuestion.question}")
                    Log.d("QuizViewModel", "Doğru cevap: ${apiQuestion.correct_answer}")
                    Log.d("QuizViewModel", "Yanlış cevaplar: ${apiQuestion.incorrect_answers}")
                    
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
                Log.e("QuizViewModel", "Hata detayı: ${e.message}")
                Log.e("QuizViewModel", "Hata stack trace: ${e.stackTraceToString()}")
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