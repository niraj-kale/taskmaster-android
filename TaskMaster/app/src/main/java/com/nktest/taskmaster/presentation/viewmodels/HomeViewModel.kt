package com.nktest.taskmaster.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nktest.taskmaster.domain.entities.Task
import com.nktest.taskmaster.domain.repositories.AuthRepository
import com.nktest.taskmaster.domain.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeIntent {
    object LoadTasks : HomeIntent()
    object Refresh : HomeIntent()
}

data class HomeUiState(
    val userEmail: String = "",
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val taskRepository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUser()
        handleIntent(HomeIntent.LoadTasks)
    }

    fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.LoadTasks -> loadTasks()
            is HomeIntent.Refresh -> loadTasks()
        }
    }

    private fun loadUser() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            _uiState.value = _uiState.value.copy(userEmail = user?.email ?: "")
        }
    }

    private fun loadTasks() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            if (user == null) {
                _uiState.value = _uiState.value.copy(error = "User not authenticated")
                return@launch
            }

            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val tasks = taskRepository.getAll(user.id)
                _uiState.value = _uiState.value.copy(
                    tasks = tasks,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load tasks"
                )
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}

