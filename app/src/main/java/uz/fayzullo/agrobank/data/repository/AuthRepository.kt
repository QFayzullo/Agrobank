package uz.fayzullo.agrobank.data.repository

import uz.fayzullo.agrobank.data.remote.api.AuthApi
import uz.fayzullo.agrobank.data.remote.dto.request.auth.LogOutRequest
import uz.fayzullo.agrobank.data.remote.dto.request.auth.RegisterRequest
import uz.fayzullo.agrobank.data.remote.dto.request.auth.VerifyRequest
import uz.fayzullo.agrobank.data.remote.dto.response.auth.GenericResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.LogOutResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.RegisterResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.VerifyResponse
import uz.fayzullo.agrobank.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun register(phone: String): GenericResponse<RegisterResponse> {
        val response = authApi.register(RegisterRequest(phone = phone))
        return response.body()!!
    }

    override suspend fun verifyOtp(phone: String, otp: String): GenericResponse<VerifyResponse> {
        val response = authApi.verifyOtp(VerifyRequest(phone = phone, otp = otp))
        return response.body()!!
    }

    override suspend fun logOut(refreshToken: String): GenericResponse<LogOutResponse> {
        val response = authApi.logOut(LogOutRequest(refreshToken = refreshToken))
        return response.body()!!
    }
}