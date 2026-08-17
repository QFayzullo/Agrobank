package uz.fayzullo.agrobank.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.GsonBuilder
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * SharedPreference field delegates for strongly-typed and nullable preference access.
 *
 * Available delegates:
 *
 * - `ObjectsNullable<T>` – Nullable object serialization/deserialization using Gson
 * - `Objects<T>` – Non-null object serialization/deserialization
 * - `Booleans` – Boolean preference delegate
 * - `Longs` – Long preference delegate
 * - `Ints` – Int preference delegate
 * - `Strings` – Non-null String delegate (returns "" if value is missing)
 * - `StringsNullable` – Nullable String delegate
 *
 *
 * # Creating your own storage class:
 *
 * Extend `SharedPreferenceDelegate` and use provided delegates as property delegates.
 *
 * ```
 * @Singleton
 * class LocalStorage @Inject constructor(
 *     @ApplicationContext context: Context
 * ) : SharedPreferenceDelegate(context) {
 *
 *     var accessToken: String by Strings()
 *     var refreshToken: String by Strings()
 *
 * }
 * ```
 *
 *
 * # Usage examples:
 *
 * ```
 * var authStep: InitStepValue by Objects(InitStepValue::class.java, InitStepValue.None)
 * var accessToken: String by Strings()
 * var lastTime: Long by Longs()
 * ```
 *
 *
 * # Extending with custom delegate:
 *
 * To add new preference type, inherit from `SharedPreferenceDelegate<T>` and override
 * the internal `getValue()` and `setValue()` logic for your type.
 *
 * ```
 * class FloatPref(
 *     defValue: Float = 0f
 * ) : SharedPreferenceDelegate<Float>(defValue) {
 *
 *     override fun getValue(key: String): Float =
 *         pref.getFloat(key, defValue)
 *
 *     override fun setValue(key: String, value: Float) =
 *         pref.edit().putFloat(key, value).apply()
 * }
 * ```
 *
 * This design removes code duplication and provides type-safe SharedPreference access.
 *
 * # Created by Sherzodbek Muhammadiev on 31.01.2020
 */

abstract class SharedPreferenceDelegate(context: Context, preferences: SharedPreferences? = null) {
    private val pref = preferences ?: context.getSharedPreferences(javaClass.canonicalName, Context.MODE_PRIVATE)
    protected val gson = GsonBuilder().create()

    inner class ObjectsNullable<T>(val clazz: Class<T>, private val init: T? = null) : ReadWriteProperty<Any, T?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T? {
            val json = pref.getString(property.name, null) ?: return init
            return gson.fromJson(json, clazz)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T?) = pref.edit { putString(property.name, value?.let { gson.toJson(it) }).apply() }
    }

    inner class Objects<T>(val clazz: Class<T>, private val init: T) : ReadWriteProperty<Any, T> {
        override fun getValue(thisRef: Any, property: KProperty<*>): T {
            val json = pref.getString(property.name, null) ?: return init
            return gson.fromJson(json, clazz)
        }

        override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = pref.edit { putString(property.name, value?.let { gson.toJson(it) }).apply() }
    }

    inner class Booleans(private val init: Boolean = false) : ReadWriteProperty<Any, Boolean> {
        override fun getValue(thisRef: Any, property: KProperty<*>) = pref.getBoolean(property.name, init)
        override fun setValue(thisRef: Any, property: KProperty<*>, value: Boolean) = pref.edit { putBoolean(property.name, value).apply() }
    }

    inner class Ints(private val defValue: Int = 0) : ReadWriteProperty<Any, Int> {
        override fun getValue(thisRef: Any, property: KProperty<*>) = pref.getInt(property.name, defValue)
        override fun setValue(thisRef: Any, property: KProperty<*>, value: Int) = pref.edit { putInt(property.name, value).apply() }
    }

    inner class Longs(private val defValue: Long = 0L) : ReadWriteProperty<Any, Long> {
        override fun getValue(thisRef: Any, property: KProperty<*>) = pref.getLong(property.name, defValue)
        override fun setValue(thisRef: Any, property: KProperty<*>, value: Long) = pref.edit { putLong(property.name, value).apply() }
    }

    inner class Strings(private val defValue: String = "") : ReadWriteProperty<Any, String> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String = pref.getString(property.name, defValue) ?: ""

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String) = pref.edit { putString(property.name, value).apply() }
    }

    inner class StringsNullable(private val defValue: String? = null) : ReadWriteProperty<Any, String?> {
        override fun getValue(thisRef: Any, property: KProperty<*>): String? = pref.getString(property.name, defValue)

        override fun setValue(thisRef: Any, property: KProperty<*>, value: String?) =
            value?.run { pref.edit { putString(property.name, value).apply() } } ?: Unit
    }
}
