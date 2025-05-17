package com.example.labassignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labassignment2.data.TodoEntity
import com.example.labassignment2.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _todo = MutableStateFlow<TodoEntity?>(null)
    val todo: StateFlow<TodoEntity?> = _todo

    fun loadTodo(id: Int) {
        viewModelScope.launch {
            val item = repository.getTodoById(id)
            println("Todo loaded for ID $id: $item") // Debug log
            _todo.value = item
        }
    }
}