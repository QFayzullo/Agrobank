package uz.fayzullo.agrobank.data.remote.dto.response.auth

data class GenericResponse<T>(
     val success: Boolean,
    val data:T
)