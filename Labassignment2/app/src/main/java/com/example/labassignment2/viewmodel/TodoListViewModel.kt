package com.example.labassignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labassignment2.model.Todo
import com.example.labassignment2.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

sealed class TodoUiState {
    object Loading : TodoUiState()
    data class Success(val todos: List<Todo>) : TodoUiState()
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
            // Collect cached todos from Room immediately
            repository.todos.collectLatest { todos ->
                if (todos.isNotEmpty()) {
                    _uiState.value = TodoUiState.Success(todos)
                } else {
                    _uiState.value = TodoUiState.Loading
                }
            }
        }
        // Trigger network refresh in the background
        viewModelScope.launch {
            try {
                repository.refreshTodos()
            } catch (e: UnknownHostException) {
                if (_uiState.value !is TodoUiState.Success) {
                    _uiState.value = TodoUiState.Error("No network connection, and no cached data available")
                }
            } catch (e: Exception) {
                if (_uiState.value !is TodoUiState.Success) {
                    _uiState.value = TodoUiState.Error("Failed to load todos: ${e.message}")
                }
            }
        }
    }
}