package com.example.labassignment2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.labassignment2.theme.AppNavGraph
import com.example.labassignment2.theme.LabAssignment2Theme
import com.example.labassignment2.viewmodel.TodoDetailViewModel
import com.example.labassignment2.viewmodel.TodoListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val listViewModel: TodoListViewModel by viewModels()
    private val detailViewModel: TodoDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LabAssignment2Theme{
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    AppNavGraph(
                        navController = navController,
                        listViewModel = listViewModel,
                        detailViewModel = detailViewModel
                    )
                }
            }
        }
    }
}