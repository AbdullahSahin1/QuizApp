package com.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int
)

data class QuizUiState(
    val questions: List<QuizQuestion> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedAnswerIndex: Int? = null,
    val score: Int = 0,
    val isQuizCompleted: Boolean = false,
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
        _uiState.update { it.copy(isLoading = true) }
        
        // Örnek sorular
        val sampleQuestions = listOf(
            QuizQuestion(
                id = 1,
                question = "Türkiye'nin başkenti neresidir?",
                options = listOf("İstanbul", "Ankara", "İzmir", "Bursa"),
                correctAnswerIndex = 1
            ),
            QuizQuestion(
                id = 2,
                question = "Hangi gezegen Güneş Sisteminin en büyük gezegenidir?",
                options = listOf("Mars", "Venüs", "Jüpiter", "Satürn"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 3,
                question = "İnsan vücudundaki en büyük organ hangisidir?",
                options = listOf("Kalp", "Beyin", "Deri", "Karaciğer"),
                correctAnswerIndex = 2
            ),
            QuizQuestion(
                id = 4,
                question = "Hangi element periyodik tabloda 'Fe' sembolü ile gösterilir?",
                options = listOf("Demir", "Flor", "Fosfor", "Fermiyum"),
                correctAnswerIndex = 0
            ),
            QuizQuestion(
                id = 5,
                question = "Hangi yıl Türkiye Cumhuriyeti kurulmuştur?",
                options = listOf("1920", "1921", "1922", "1923"),
                correctAnswerIndex = 3
            )
        )
        
        _uiState.update { 
            it.copy(
                questions = sampleQuestions,
                isLoading = false
            )
        }
    }

    fun selectAnswer(answerIndex: Int) {
        val currentState = _uiState.value
        val currentQuestion = currentState.questions.getOrNull(currentState.currentQuestionIndex) ?: return
        
        _uiState.update { it.copy(selectedAnswerIndex = answerIndex) }
        
        // Cevap doğru mu kontrol et
        if (answerIndex == currentQuestion.correctAnswerIndex) {
            _uiState.update { it.copy(score = it.score + 1) }
        }
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        
        if (currentState.currentQuestionIndex < currentState.questions.size - 1) {
            _uiState.update { 
                it.copy(
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    selectedAnswerIndex = null
                )
            }
        } else {
            _uiState.update { it.copy(isQuizCompleted = true) }
        }
    }

    fun restartQuiz() {
        _uiState.update { 
            QuizUiState(
                questions = it.questions,
                currentQuestionIndex = 0,
                selectedAnswerIndex = null,
                score = 0,
                isQuizCompleted = false
            )
        }
    }
} 