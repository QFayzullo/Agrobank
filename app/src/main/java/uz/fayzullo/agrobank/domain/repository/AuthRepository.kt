package uz.fayzullo.agrobank.domain.repository

import uz.fayzullo.agrobank.data.remote.dto.response.auth.GenericResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.LogOutResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.RegisterResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.VerifyResponse

interface AuthRepository {
    suspend fun register(phone: String): GenericResponse<RegisterResponse>
    suspend fun verifyOtp(phone: String, otp: String): GenericResponse<VerifyResponse>
    suspend fun logOut(refreshToken: String): GenericResponse<LogOutResponse>
}