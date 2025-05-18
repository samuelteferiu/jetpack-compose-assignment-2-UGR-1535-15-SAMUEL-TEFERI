package com.example.labassignment2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labassignment2.data.TodoEntity
import com.example.labassignment2.model.Todo
import com.example.labassignment2.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoDetailViewModel @Inject constructor(
    private val repository: TodoRepository
) : ViewModel() {

    private val _todo = MutableStateFlow<Todo?>(null)
    val todo: StateFlow<Todo?> = _todo

    fun loadTodo(id: Int) {
        viewModelScope.launch {
            val item = repository.getTodoById(id)?.toTodo()
            _todo.value = item
        }
    }

    // Convert TodoEntity to Todo
    private fun TodoEntity.toTodo() = Todo(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )
}