package uz.fayzullo.agrobank.di

import android.content.Context
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import uz.fayzullo.agrobank.BuildConfig
import uz.fayzullo.agrobank.data.local.LocalStorage
import uz.fayzullo.agrobank.data.remote.api.AuthApi
import java.util.concurrent.TimeUnit
import uz.fayzullo.agrobank.data.remote.AuthInterceptor

object NetworkModule {

    private const val BASE_URL = "http://173.212.244.180:8080/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        isLenient = true
    }

    private var localStorage: LocalStorage? = null
    private var authApi: AuthApi? = null

    fun init(context: Context) {
        if (localStorage != null) return
        localStorage = LocalStorage(context.applicationContext)
    }

    fun getLocalStorage(): LocalStorage =
        localStorage ?: throw IllegalStateException(
            "NetworkModule.init(context) chaqirilmagan (Application onCreate'da chaqiring)"
        )

    private fun buildOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(getLocalStorage()))
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun buildRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildOkHttpClient())
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    fun getAuthApi(): AuthApi {
        return authApi ?: buildRetrofit().create(AuthApi::class.java).also {
            authApi = it
        }
    }
}
