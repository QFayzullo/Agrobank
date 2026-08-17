package uz.fayzullo.agrobank.presentation.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fayzullo.agrobank.data.repository.AuthRepositoryImpl
import uz.fayzullo.agrobank.di.NetworkModule
import uz.fayzullo.agrobank.domain.repository.AuthRepository
import uz.fayzullo.agrobank.presentation.screens.register.RegisterUiState

class HomeViewModel(
    private val repository: AuthRepository = AuthRepositoryImpl(NetworkModule.getAuthApi())
) : ViewModel() {

    var logOutState by mutableStateOf<RegisterUiState>(RegisterUiState.Idle)
        private set

    fun logOut() {
        viewModelScope.launch {
            logOutState = RegisterUiState.Loading
            val localStorage = NetworkModule.getLocalStorage()
            val refreshToken = localStorage.refreshToken

            runCatching {
                repository.logOut(refreshToken)
            }

            localStorage.accessToken = ""
            localStorage.refreshToken = ""
            logOutState = RegisterUiState.Success
        }
    }
}