package uz.fayzullo.agrobank

import android.app.Application
import uz.fayzullo.agrobank.di.NetworkModule

class AgrobankApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkModule.init(this)
    }
}