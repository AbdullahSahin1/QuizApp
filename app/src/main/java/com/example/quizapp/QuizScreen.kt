package com.example.quizapp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.quizapp.viewmodel.QuizViewModel

@Composable
fun QuizScreen(
    onNavigateToLogin: () -> Unit,
    viewModel: QuizViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val error = uiState.error

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Üst bilgi çubuğu
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Soru ${uiState.currentQuestionIndex + 1}/${uiState.questions.size}",
                style = MaterialTheme.typography.titleMedium
            )
            
            Text(
                text = "Puan: ${uiState.score}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.isQuizCompleted) {
            // Quiz tamamlandı ekranı
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Quiz Tamamlandı!",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Text(
                    text = "Toplam Puanınız: ${uiState.score}/${uiState.questions.size}",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                Button(
                    onClick = { viewModel.restartQuiz() },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Tekrar Başla")
                }
                
                Button(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text("Çıkış Yap")
                }
            }
        } else {
            // Soru ekranı
            val currentQuestion = uiState.questions.getOrNull(uiState.currentQuestionIndex)
            
            if (currentQuestion != null) {
                Text(
                    text = currentQuestion.question,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
                
                // Cevap seçenekleri
                currentQuestion.options.forEachIndexed { index, option ->
                    val isSelected = uiState.selectedAnswerIndex == index
                    val isCorrect = index == currentQuestion.correctAnswerIndex
                    
                    // Renk belirleme
                    val backgroundColor = when {
                        isSelected && isCorrect -> Color(0xFF4CAF50) // Doğru cevap
                        isSelected && !isCorrect -> Color(0xFFE57373) // Yanlış cevap
                        !isSelected && isCorrect && uiState.selectedAnswerIndex != null -> Color(0xFF4CAF50) // Doğru cevap gösterimi
                        else -> MaterialTheme.colorScheme.surface
                    }
                    
                    val borderColor = when {
                        isSelected -> Color.Transparent
                        else -> MaterialTheme.colorScheme.outline
                    }
                    
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(backgroundColor)
                            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                            .clickable(enabled = uiState.selectedAnswerIndex == null) {
                                viewModel.selectAnswer(index)
                            }
                            .padding(16.dp)
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (isSelected || (!isSelected && isCorrect && uiState.selectedAnswerIndex != null)) Color.White else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                
                Spacer(modifier = Modifier.weight(1f))
                
                // İleri butonu
                if (uiState.selectedAnswerIndex != null) {
                    Button(
                        onClick = { viewModel.nextQuestion() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        Text(
                            text = if (uiState.currentQuestionIndex < uiState.questions.size - 1) {
                                "Sonraki Soru"
                            } else {
                                "Quiz'i Bitir"
                            }
                        )
                    }
                }
            }
        }
    }
} 