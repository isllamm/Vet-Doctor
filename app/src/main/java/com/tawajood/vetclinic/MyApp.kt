package com.tawajood.vetclinic

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.tawajood.vetclinic.data.PrefsHelper
import com.tawajood.vetclinic.utils.updateLanguage
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class MyApp: Application() {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        PrefsHelper.init(applicationContext)
        Lingver.init(this, Locale("ar", "EG"))
        updateLanguage(applicationContext)
    }
}