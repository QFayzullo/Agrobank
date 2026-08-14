package uz.fayzullo.agrobank.data.remote.dto.response.auth

data class GenericErrorResponse<T>(
     val success: Boolean,
    val error:T
)
/*
{
  "success": false,
  "error": {
    "code": "INVALID_OTP",
    "message": "The OTP code is invalid or has expired."
  }
}
 */