package uz.fayzullo.agrobank.data.remote.dto.response.auth

data class VerifyErrorResponse(
    val code: String,
    val message: String,
)
/*
{
  "success": true,
  "data": {
    "code": "string",
    "message": "string",
  }
}
 */