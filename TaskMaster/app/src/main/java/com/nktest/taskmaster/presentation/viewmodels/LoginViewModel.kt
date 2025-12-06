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

sealed class LoginIntent {
    data class EmailChanged(val email: String) : LoginIntent()
    data class PasswordChanged(val password: String) : LoginIntent()
    object Login : LoginIntent()
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: LoginIntent) {
        when (intent) {
            is LoginIntent.EmailChanged -> {
                _uiState.value = _uiState.value.copy(email = intent.email, error = null)
            }
            is LoginIntent.PasswordChanged -> {
                _uiState.value = _uiState.value.copy(password = intent.password, error = null)
            }
            is LoginIntent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        val state = _uiState.value
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Email and password are required")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)
            when (val result = authRepository.signIn(state.email, state.password)) {
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

