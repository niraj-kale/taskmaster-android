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
import java.util.UUID
import javax.inject.Inject

sealed class CreateTaskIntent {
    data class TitleChanged(val title: String) : CreateTaskIntent()
    data class DescriptionChanged(val description: String) : CreateTaskIntent()
    object Save : CreateTaskIntent()
    object Cancel : CreateTaskIntent()
}

data class CreateTaskUiState(
    val title: String = "",
    val description: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CreateTaskUiState())
    val uiState: StateFlow<CreateTaskUiState> = _uiState.asStateFlow()

    fun handleIntent(intent: CreateTaskIntent) {
        when (intent) {
            is CreateTaskIntent.TitleChanged -> {
                _uiState.value = _uiState.value.copy(title = intent.title, error = null)
            }
            is CreateTaskIntent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(description = intent.description, error = null)
            }
            is CreateTaskIntent.Save -> {
                saveTask()
            }
            is CreateTaskIntent.Cancel -> {
                // Cancel handled by screen navigation
            }
        }
    }

    private fun saveTask() {
        val state = _uiState.value
        if (state.title.isBlank()) {
            _uiState.value = state.copy(error = "Title is required")
            return
        }

        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            if (user == null) {
                _uiState.value = state.copy(error = "User not authenticated")
                return@launch
            }

            _uiState.value = state.copy(isLoading = true, error = null)
            try {
                val task = Task(
                    id = UUID.randomUUID().toString(),
                    title = state.title,
                    description = state.description,
                    createdAt = System.currentTimeMillis(),
                    userId = user.id
                )
                taskRepository.create(task)
                _uiState.value = state.copy(isLoading = false, isSuccess = true)
            } catch (e: Exception) {
                _uiState.value = state.copy(isLoading = false, error = e.message ?: "Failed to create task")
            }
        }
    }
}

