package com.example.quizapp.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class RegisterUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class RegisterViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update { it.copy(confirmPassword = confirmPassword) }
    }

    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun register() {
        val currentState = _uiState.value
        
        // Basit doğrulama
        if (currentState.email.isEmpty() || currentState.password.isEmpty() || 
            currentState.confirmPassword.isEmpty() || currentState.username.isEmpty()) {
            _uiState.update { it.copy(error = "Tüm alanları doldurunuz") }
            return
        }

        if (currentState.password != currentState.confirmPassword) {
            _uiState.update { it.copy(error = "Şifreler eşleşmiyor") }
            return
        }

        _uiState.update { it.copy(isLoading = true, error = null) }
        // TODO: Kayıt işlemleri burada yapılacak
    }
} 