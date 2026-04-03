package com.example.unit_test_basic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.unit_test_basic.ui.screens.home.HomeScreen
import com.example.unit_test_basic.ui.screens.login.LoginScreen
import com.example.unit_test_basic.ui.screens.login.LoginViewModel
import com.example.unit_test_basic.ui.screens.register.RegisterScreen
import com.example.unit_test_basic.ui.screens.register.RegisterViewModel
import com.example.unit_test_basic.ui.theme.UnittestbasicTheme
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UnittestbasicTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // Halaman Login
        composable("login") {
            val loginViewModel: LoginViewModel = viewModel()
            LoginScreen(
                onLoginSuccess = { email ->
                    navController.navigate("home/$email") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                },
                viewModel = loginViewModel
            )
        }

        // Halaman Register
        composable("register") {
            val registerViewModel: RegisterViewModel = viewModel()
            RegisterScreen(
                onRegisterClick = {
                    navController.popBackStack()
                },
                onBackToLoginClick = {
                    navController.popBackStack()
                },
                viewModel = registerViewModel
            )
        }

        // Halaman Home
        composable(
            route = "home/{userName}",
            arguments = listOf(
                navArgument("userName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName") ?: "User"
            HomeScreen(
                userName = userName,
                onLogoutClick = {
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}
