package com.example.quizapp.ui.quiz

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
        loadQuestions()
    }

    private fun loadQuestions() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val response = RetrofitClient.quizApiService.getQuestions()
                val questions = response.results.map { apiQuestion ->
                    QuizQuestion(
                        question = apiQuestion.question,
                        options = (apiQuestion.incorrectAnswers + apiQuestion.correctAnswer).shuffled(),
                        correctAnswer = apiQuestion.correctAnswer
                    )
                }
                _uiState.value = _uiState.value.copy(
                    questions = questions,
                    isLoading = false
                )
            } catch (e: Exception) {
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