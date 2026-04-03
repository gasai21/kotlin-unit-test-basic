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
import com.example.unit_test_basic.ui.screens.register.RegisterScreen
import com.example.unit_test_basic.ui.theme.UnittestbasicTheme

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
            LoginScreen(
                onLoginSuccess = { email ->
                    // Navigasi ke home sambil membawa email sebagai argumen
                    navController.navigate("home/$email") {
                        // Hapus login dari backstack agar tidak bisa kembali ke login setelah masuk
                        popUpTo("login") { inclusive = true }
                    }
                },
                onRegisterClick = {
                    navController.navigate("register")
                }
            )
        }

        // Halaman Register
        composable("register") {
            RegisterScreen(
                onRegisterClick = {
                    navController.popBackStack()
                },
                onBackToLoginClick = {
                    navController.popBackStack()
                }
            )
        }

        // Halaman Home dengan Argumen (Data User)
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
                        // Bersihkan seluruh stack saat logout
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
    }
}