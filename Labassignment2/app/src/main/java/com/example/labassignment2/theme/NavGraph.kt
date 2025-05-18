package com.example.labassignment2.theme



import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.labassignment2.viewmodel.TodoListViewModel
import com.example.labassignment2.viewmodel.TodoDetailViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    listViewModel: TodoListViewModel,
    detailViewModel: TodoDetailViewModel
) {
    NavHost(navController = navController, startDestination = "todo_list") {
        composable("todo_list") {
            TodoListScreen(
                viewModel = listViewModel,
                onTodoClick = { todoId ->
                    navController.navigate("todo_detail/$todoId")
                }
            )
        }
        composable("todo_detail/{todoId}") { backStackEntry ->
            val todoId = backStackEntry.arguments?.getString("todoId")?.toIntOrNull() ?: return@composable
            TodoDetailScreen(viewModel = detailViewModel, todoId = todoId, onBack = {
                navController.popBackStack()
            })
        }
    }
}
