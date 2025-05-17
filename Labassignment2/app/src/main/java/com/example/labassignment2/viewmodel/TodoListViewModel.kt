package com.example.labassignment2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labassignment2.data.TodoEntity
import com.example.labassignment2.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class TodoUiState {
    object Loading : TodoUiState()
    data class Success(val todos: List<TodoEntity>) : TodoUiState()
    data class Error(val message: String) : TodoUiState()
}

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<TodoUiState>(TodoUiState.Loading)
    val uiState: StateFlow<TodoUiState> = _uiState

    init {
        fetchTodos()
    }

    private fun fetchTodos() {
        viewModelScope.launch {
            _uiState.value = TodoUiState.Loading
            try {
                val todos = repository.getTodos()
                println("Todos from repository: $todos") // Debug log
                _uiState.value = TodoUiState.Success(todos)
            } catch (e: Exception) {
                _uiState.value = TodoUiState.Error("Failed to load todos")
            }
        }
    }
}