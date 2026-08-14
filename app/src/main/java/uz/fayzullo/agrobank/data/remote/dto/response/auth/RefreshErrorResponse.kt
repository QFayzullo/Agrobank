package uz.fayzullo.agrobank.data.remote.dto.response.auth

data class RefreshErrorResponse(
    val code: String,
    val message: String,
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