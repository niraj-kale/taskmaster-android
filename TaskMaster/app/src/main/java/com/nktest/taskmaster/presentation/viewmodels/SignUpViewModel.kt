package com.nktest.taskmaster.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nktest.taskmaster.domain.entities.AuthResult
import com.nktest.taskmaster.domain.repositories.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SignUpIntent {
    data class EmailChanged(val email: String) : SignUpIntent()
    data class PasswordChanged(val password: String) : SignUpIntent()
    object SignUp : SignUpIntent()
}

data class SignUpUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: SignUpIntent) {
        when (intent) {
            is SignUpIntent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = intent.email, error = null)
            }
            is SignUpIntent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = intent.password, error = null)
            }
            is SignUpIntent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Email and password are required")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)
            when (val result = authRepository.signUp(state.email, state.password)) {
                is AuthResult.Success -> {
                    _uiState.value = state.copy(isLoading = false, isSuccess = true)
                }
                is AuthResult.Error -> {
                    _uiState.value = state.copy(isLoading = false, error = result.message)
                }
            }
        }
    }
}

