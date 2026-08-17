package uz.fayzullo.agrobank.data.remote

import okhttp3.Interceptor
import okhttp3.Response
import uz.fayzullo.agrobank.data.local.LocalStorage

class AuthInterceptor(
    private val localStorage: LocalStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val accessToken = localStorage.accessToken

        val request = if (accessToken.isNotEmpty()) {
            original.newBuilder()
                .addHeader("Authorization", "Bearer $accessToken")
                .build()
        } else {
            original
        }

        return chain.proceed(request)
    }
}