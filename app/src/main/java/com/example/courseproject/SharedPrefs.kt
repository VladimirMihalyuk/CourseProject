package com.example.courseproject

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

val prefs: Prefs by lazy {
    App.prefs!!
}

class App : Application() {
    companion object {
        var prefs: Prefs? = null
    }

    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
    }
}

class Prefs (context: Context) {
    val PREFS_FILENAME = " com.example.prpsapp.prefsField"
    val LOG_IN_ID = "log_in_id"
    val prefsField: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0);

    var idClient: String?
        get() = prefsField.getString(LOG_IN_ID, null)
        set(value : String?) = prefsField.edit().putString(LOG_IN_ID, value).apply()
}