package uz.fayzullo.agrobank.data.local

import android.content.Context
import uz.fayzullo.agrobank.utils.SharedPreferenceDelegate

class LocalStorage(context: Context) : SharedPreferenceDelegate(context) {
    var accessToken: String by Strings()
    var refreshToken: String by Strings()
}
