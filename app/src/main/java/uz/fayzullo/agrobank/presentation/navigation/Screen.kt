package uz.fayzullo.agrobank.presentation.navigation

sealed class Screen(val route:String) {
    object LanguageSelectionScreen: Screen("languageSelection")
    object RegisterPhoneScreen: Screen("register")
    object RegisterCodeScreen: Screen("registerCode")
    object RegisterPasswordScreen: Screen("registerPassword")
}