package uz.fayzullo.agrobank.data.remote.dto.response.auth

data class RefreshResponse(
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean
)
/*
{
  "success": true,
  "data": {
    "accessToken": "string",
    "refreshToken": "string",
    "isNewUser": false
  }
}
 */