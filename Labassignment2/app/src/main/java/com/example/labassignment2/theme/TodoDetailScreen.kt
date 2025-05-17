package com.example.labassignment2.theme

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.labassignment2.viewmodel.TodoDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(
    viewModel: TodoDetailViewModel,
    todoId: Int,
    onBack: () -> Unit
) {
    val todo = viewModel.todo.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.loadTodo(todoId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Todo Detail", color = Color.White)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Blue
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (todo != null) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "ID: ${todo.id}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Title: ${todo.title}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Status: ${if (todo.completed) "Completed" else "Pending"}")
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}
