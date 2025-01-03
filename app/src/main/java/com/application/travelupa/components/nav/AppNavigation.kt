package com.application.travelupa.components.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.application.travelupa.components.GreetingScreen
import com.application.travelupa.components.auth.LoginScreen
import com.application.travelupa.components.auth.RegisterScreen
import com.application.travelupa.components.wisata.RekomendasiTempatScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


@Composable
fun AppNavigation(currentUser: FirebaseUser?) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if(currentUser != null)

   Screen.Greeting.route else Screen.Greeting.route
    ) {

        composable(Screen.Greeting.route){
            GreetingScreen(
                onStart = {
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Greeting.route){
                            inclusive = true
                        }

                    }
                }
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.RekomendasiTempat.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.RekomendasiTempat.route) {
            RekomendasiTempatScreen(
                onBackToLogin = {
                    FirebaseAuth.getInstance().signOut()
                    navController.navigate(Screen.Greeting.route){
                        popUpTo(Screen.RekomendasiTempat.route){
                            inclusive = true
                        }

                    }
                }
            )
        }
    }
}