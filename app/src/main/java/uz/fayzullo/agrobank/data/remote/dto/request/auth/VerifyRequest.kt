package uz.fayzullo.agrobank.data.remote.dto.request.auth

data class VerifyRequest(
    val phone: String,
    val otp: String
)
/*
{
  "phone": "+998901234567",
  "otp": "482931"
}
 */