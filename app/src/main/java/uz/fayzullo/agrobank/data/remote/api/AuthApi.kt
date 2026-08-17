package uz.fayzullo.agrobank.data.remote.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.fayzullo.agrobank.data.remote.dto.request.auth.LogOutRequest
import uz.fayzullo.agrobank.data.remote.dto.request.auth.RefreshRequest
import uz.fayzullo.agrobank.data.remote.dto.request.auth.RegisterRequest
import uz.fayzullo.agrobank.data.remote.dto.request.auth.VerifyRequest
import uz.fayzullo.agrobank.data.remote.dto.response.auth.GenericResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.LogOutResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.RefreshResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.RegisterResponse
import uz.fayzullo.agrobank.data.remote.dto.response.auth.VerifyResponse

interface AuthApi {
    @POST("v1/auth/send-otp")
    suspend fun register(@Body request: RegisterRequest): Response<GenericResponse<RegisterResponse>>

    @POST("v1/auth/verify-otp")
    suspend fun verifyOtp(@Body request: VerifyRequest): Response<GenericResponse<VerifyResponse>>

    @POST("v1/auth/refresh")
    suspend fun refresh(@Body refreshRequest: RefreshRequest): Response<GenericResponse<RefreshResponse>>

    @POST("v1/auth/logout")
    suspend fun logOut(@Body request: LogOutRequest):Response<GenericResponse<LogOutResponse>>

}
