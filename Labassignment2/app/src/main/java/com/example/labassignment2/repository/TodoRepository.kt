package com.example.labassignment2.repository

import com.example.labassignment2.data.TodoDao
import com.example.labassignment2.data.TodoEntity
import com.example.labassignment2.network.TodoApiService
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val api: TodoApiService,
    private val dao: TodoDao
) {

    private val sampleTodos = listOf(
        TodoEntity(
            userId = 1,
            id = 1,
            title = "Complete Mobile Assignment",
            completed = false
        ),
        TodoEntity(
            userId = 1,
            id = 2,
            title = "Buy New Laptop",
            completed = true
        ),
        TodoEntity(
            userId = 1,
            id = 3,
            title = "Schedule Laptop Clinic  appointment",
            completed = true
        ),
          TodoEntity(
            userId = 1,
            id = 6,
            title = "Call Clinic",
            completed = false
        ),
        TodoEntity(
            userId = 1,
            id = 4,
            title = "Read a OS for 30 minutes",
            completed = false
        ),
        TodoEntity(
            userId = 1,
            id = 5,
            title = "Go to the gym",
            completed = true
        )
      
    )

    suspend fun getTodos(): List<TodoEntity> {
        dao.deleteAllTodos()
        dao.insertTodos(sampleTodos) 
        val todos = dao.getAllTodos()
        println("Todos loaded: $todos") 
        return todos
    }

    suspend fun getTodoById(id: Int): TodoEntity? {
        dao.deleteAllTodos()
        dao.insertTodos(sampleTodos) 
        val todos = dao.getAllTodos()
        println("Todos loaded for ID $id: $todos")
        return todos.find { it.id == id }
    }
}