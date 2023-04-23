package com.example.harganow.presentation.navigation

import LoginScreen
import RegisterScreen
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.harganow.presentation.announcement.AnnouncementScreen
import com.example.harganow.presentation.login.HomeScreen
import com.example.harganow.presentation.user.*

@Composable
fun MyApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "forget_password") {
        composable("home") { HomeScreen(
                navigateToLogin = { navController.navigate("login") },
                navigateToRegister = { navController.navigate("register") },
        ) }
        composable("login") { LoginScreen(
                navigateToPreviousStack = { navController.popBackStack() },
                navigateToRegister = { navController.navigate("register") },
                // TODO: Change this to navigate to the main screen
                navigateToMain = { navController.navigate("user_settings") },
        ) }
        composable("register") { RegisterScreen(
                navigateToPreviousStack = { navController.popBackStack() },
                navigateToLogin = { navController.navigate("login") },
        ) }
        composable("user_settings"){
            UserSettingsScreen(
                navigateToPreviousStack = { navController.popBackStack() },
                navigateToManageAccount = { navController.navigate("manage_account") },
                navigateToAddress = {},
                navigateToBankAccount = {navController.navigate("bank_accounts_cards")},
                navigateToPolicy = {navController.navigate("policy") },
                navigateToHome = {navController.navigate("home") },
            )
        }
        composable("policy"){
            PolicyScreen(
                navigateToPreviousStack = { navController.popBackStack() },
        ) }
        composable("manage_account") {
            ManageAccountScreen(
                navigateToPreviousStack = { navController.popBackStack() },
                navigateToForgetPassword = { navController.navigate("forget_password") }
        ) }
        composable("reset_password") {
            ResetPasswordScreen(
                navigateToPreviousStack = { navController.popBackStack() },
                navigateToForgetPassword = { navController.navigate("forget_password")  },
        ) }
        composable("forget_password") {
            ForgetPasswordScreen(
                navigateToPreviousStack = { navController.popBackStack() },
        ) }
        composable("announcement"){
            AnnouncementScreen(
                navigateToPreviousStack = { navController.popBackStack() },
        ) }
        composable("bank_accounts_cards"){
            BankAccountsCardsScreen(
                navigateToPreviousStack = { navController.popBackStack() },
                navigateToAddBankAccount = { },
                navigateToAddCard = { },
        ) }
    }
}