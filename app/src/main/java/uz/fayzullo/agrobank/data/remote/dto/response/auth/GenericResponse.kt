package uz.fayzullo.agrobank.data.remote.dto.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class GenericResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val error: ErrorBody? = null
)

@Serializable
data class ErrorBody(
    val code: String,
    val message: String
)