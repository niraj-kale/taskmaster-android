package com.nktest.taskmaster.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.SavedStateHandle
import com.nktest.taskmaster.domain.entities.Task
import com.nktest.taskmaster.domain.repositories.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class TaskDetailIntent {
    object LoadTask : TaskDetailIntent()
    data class TitleChanged(val title: String) : TaskDetailIntent()
    data class DescriptionChanged(val description: String) : TaskDetailIntent()
    object Update : TaskDetailIntent()
    object Delete : TaskDetailIntent()
    object NavigateBack : TaskDetailIntent()
}

data class TaskDetailUiState(
    val task: Task? = null,
    val title: String = "",
    val description: String = "",
    val isEditMode: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false,
    val showDeleteConfirmation: Boolean = false
)

@HiltViewModel
class TaskDetailViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val taskId: String = savedStateHandle.get<String>("taskId") ?: ""

    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()

    init {
        handleIntent(TaskDetailIntent.LoadTask)
    }

    fun handleIntent(intent: TaskDetailIntent) {
        when (intent) {
            is TaskDetailIntent.LoadTask -> loadTask()
            is TaskDetailIntent.TitleChanged -> {
                _uiState.value = _uiState.value.copy(title = intent.title, error = null)
            }
            is TaskDetailIntent.DescriptionChanged -> {
                _uiState.value = _uiState.value.copy(description = intent.description, error = null)
            }
            is TaskDetailIntent.Update -> updateTask()
            is TaskDetailIntent.Delete -> {
                _uiState.value = _uiState.value.copy(showDeleteConfirmation = true)
            }
            is TaskDetailIntent.NavigateBack -> {
                // Navigation handled by screen
            }
        }
    }

    fun toggleEditMode() {
        val state = _uiState.value
        if (state.isEditMode) {
            _uiState.value = state.copy(
                isEditMode = false,
                title = state.task?.title ?: "",
                description = state.task?.description ?: ""
            )
        } else {
            _uiState.value = state.copy(
                isEditMode = true,
                title = state.task?.title ?: "",
                description = state.task?.description ?: ""
            )
        }
    }

    fun confirmDelete() {
        deleteTask()
    }

    fun cancelDelete() {
        _uiState.value = _uiState.value.copy(showDeleteConfirmation = false)
    }

    private fun loadTask() {
        if (taskId.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Task ID is missing")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)
            try {
                val task = taskRepository.getById(taskId)
                if (task != null) {
                    _uiState.value = _uiState.value.copy(
                        task = task,
                        title = task.title,
                        description = task.description,
                        isLoading = false
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Task not found"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to load task"
                )
            }
        }
    }

    private fun updateTask() {
        val state = _uiState.value
        val task = state.task ?: return

        if (state.title.isBlank()) {
            _uiState.value = state.copy(error = "Title is required")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)
            try {
                val updatedTask = task.copy(
                    title = state.title,
                    description = state.description
                )
                taskRepository.update(updatedTask)
                _uiState.value = state.copy(
                    task = updatedTask,
                    isLoading = false,
                    isSuccess = true,
                    isEditMode = false
                )
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to update task"
                )
            }
        }
    }

    private fun deleteTask() {
        val state = _uiState.value
        val task = state.task ?: return

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null, showDeleteConfirmation = false)
            try {
                taskRepository.delete(task.id)
                _uiState.value = state.copy(isLoading = false, isSuccess = true, task = null)
            } catch (e: Exception) {
                _uiState.value = state.copy(
                    isLoading = false,
                    error = e.message ?: "Failed to delete task",
                    showDeleteConfirmation = false
                )
            }
        }
    }
}

