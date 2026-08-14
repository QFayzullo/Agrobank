package uz.fayzullo.agrobank.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import uz.fayzullo.agrobank.presentation.screens.language.LanguageScreen
import uz.fayzullo.agrobank.presentation.screens.register.RegisterCode
import uz.fayzullo.agrobank.presentation.screens.register.RegisterPassword
import uz.fayzullo.agrobank.presentation.screens.register.RegisterScreen


@Composable
fun MyNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "languageSelection"
    ){
        composable(Screen.LanguageSelectionScreen.route){
           LanguageScreen(navController)
        }
        composable(Screen.RegisterPhoneScreen.route){
            RegisterScreen(navController)
        }
        composable(
            route = Screen.RegisterCodeScreen.route + "/{phoneNumber}",
            arguments = listOf(
                navArgument("phoneNumber") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val phoneNumber = backStackEntry.arguments?.getString("phoneNumber") ?: ""
            RegisterCode(phoneNumber = phoneNumber,navController)
        }
        composable(Screen.RegisterPasswordScreen.route){
            RegisterPassword(navController)
        }


    }
}