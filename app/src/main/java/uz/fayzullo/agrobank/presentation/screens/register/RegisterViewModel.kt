package uz.fayzullo.agrobank.presentation.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import uz.fayzullo.agrobank.data.repository.AuthRepositoryImpl
import uz.fayzullo.agrobank.di.NetworkModule
import uz.fayzullo.agrobank.domain.repository.AuthRepository

class RegisterViewModel(
    private val repository: AuthRepository = AuthRepositoryImpl(NetworkModule.getAuthApi())
) : ViewModel() {

    var uiState by mutableStateOf<RegisterUiState>(RegisterUiState.Idle)
        private set

    var verifyState by mutableStateOf<RegisterUiState>(RegisterUiState.Idle)
        private set

    fun sendOtp(phone: String) {
        viewModelScope.launch {
            uiState = RegisterUiState.Loading

            runCatching {
                repository.register(phone)
            }.onSuccess { response ->
                uiState = if (response.success) {
                    RegisterUiState.Success
                } else {
                    RegisterUiState.Error(response.error?.message ?: "Xatolik yuz berdi")
                }
            }.onFailure {
                uiState = RegisterUiState.Error("Internetni tekshiring")
            }
        }
    }

    fun verifyOtp(phone: String, otp: String) {
        viewModelScope.launch {
            verifyState = RegisterUiState.Loading

            runCatching {
                repository.verifyOtp(phone, otp)
            }.onSuccess { response ->
                if (response.success && response.data != null) {
                    val localStorage = NetworkModule.getLocalStorage()
                    localStorage.accessToken = response.data.accessToken
                    localStorage.refreshToken = response.data.refreshToken
                    verifyState = RegisterUiState.Success
                } else {
                    verifyState = RegisterUiState.Error(response.error?.message ?: "Kod noto'g'ri")
                }
            }.onFailure {
                verifyState = RegisterUiState.Error("Internetni tekshiring")
            }
        }
    }
}