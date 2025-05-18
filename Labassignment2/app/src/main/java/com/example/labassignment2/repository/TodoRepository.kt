package com.example.labassignment2.repository

import com.example.labassignment2.data.TodoDao
import com.example.labassignment2.data.TodoEntity
import com.example.labassignment2.model.Todo
import com.example.labassignment2.network.TodoApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.net.UnknownHostException
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val api: TodoApiService,
    private val dao: TodoDao
) {

    // Expose todos as a Flow, mapping Room entities to model
    val todos: Flow<List<Todo>> = dao.getAllTodos().map { entities ->
        entities.map { it.toTodo() }
    }

    // Refresh todos from the JSONPlaceholder API and update Room cache
    suspend fun refreshTodos() {
        try {
            val remoteTodos = api.getTodos() // Fetch from https://jsonplaceholder.typicode.com/todos
            dao.insertTodos(remoteTodos.map { it.toEntity() }) // Cache in Room
        } catch (e: UnknownHostException) {
            // Network unavailable, rely on cached data
            println("Network error: ${e.message}")
        } catch (e: Exception) {
            // Other API errors
            println("Error fetching todos: ${e.message}")
        }
    }

    // Get a single todo by ID from Room
    suspend fun getTodoById(id: Int): TodoEntity? {
        return dao.getTodoById(id)
    }

    // Convert Todo to TodoEntity for Room
    private fun Todo.toEntity() = TodoEntity(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )

    // Convert TodoEntity to Todo for UI
    private fun TodoEntity.toTodo() = Todo(
        userId = userId,
        id = id,
        title = title,
        completed = completed
    )
}