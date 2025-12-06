package com.nktest.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nktest.taskmaster.core.AuthStateManager
import com.nktest.taskmaster.presentation.screens.CreateTaskScreen
import com.nktest.taskmaster.presentation.screens.HomeScreen
import com.nktest.taskmaster.presentation.screens.LoginScreen
import com.nktest.taskmaster.presentation.screens.SignUpScreen
import com.nktest.taskmaster.presentation.screens.TaskDetailScreen
import com.nktest.taskmaster.ui.theme.TaskMasterTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var authStateManager: AuthStateManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskMasterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    LaunchedEffect(Unit) {
                        val isAuthenticated = authStateManager.isAuthenticated()
                        if (isAuthenticated) {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = "login"
                    ) {
                        composable("login") {
                            LoginScreen(
                                onNavigateToSignUp = { navController.navigate("signup") },
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("login") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("signup") {
                            SignUpScreen(
                                onNavigateToLogin = { navController.navigate("login") },
                                onNavigateToHome = {
                                    navController.navigate("home") {
                                        popUpTo("signup") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                onNavigateToLogin = {
                                    navController.navigate("login") {
                                        popUpTo("home") { inclusive = true }
                                    }
                                },
                                onNavigateToCreateTask = {
                                    navController.navigate("createTask")
                                },
                                onNavigateToTaskDetail = { taskId ->
                                    navController.navigate("taskDetail/$taskId")
                                }
                            )
                        }
                        composable("createTask") {
                            CreateTaskScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(
                            "taskDetail/{taskId}",
                            arguments = listOf(navArgument("taskId") { type = NavType.StringType })
                        ) {
                            TaskDetailScreen(
                                onNavigateBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}