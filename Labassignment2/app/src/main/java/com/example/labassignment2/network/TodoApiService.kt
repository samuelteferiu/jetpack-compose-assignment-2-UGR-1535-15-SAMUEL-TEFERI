package com.example.labassignment2.network

import com.example.labassignment2.model.Todo
import retrofit2.http.GET

interface TodoApiService {
    @GET("todos")
    suspend fun getTodos(): List<Todo>
}