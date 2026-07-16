package com.medirecord

import android.app.Application
import com.medirecord.util.AdManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MediRecordApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AdManager.initialize(this)
    }
}
