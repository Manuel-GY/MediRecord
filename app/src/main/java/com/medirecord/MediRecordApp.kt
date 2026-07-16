package com.medirecord

import android.app.Application
import com.medirecord.util.AdManager
import com.medirecord.util.BillingManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@HiltAndroidApp
class MediRecordApp : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    val billingManager by lazy { BillingManager(this) }

    override fun onCreate() {
        super.onCreate()
        AdManager.initialize(this)

        val prefs = getSharedPreferences("medirecord_prefs", MODE_PRIVATE)
        val isPremium = prefs.getBoolean("is_premium", false)

        if (isPremium) {
            AdManager.setPremium(true)
        }

        applicationScope.launch {
            billingManager.isPremium.collect { premium ->
                if (premium) {
                    AdManager.setPremium(true)
                    prefs.edit().putBoolean("is_premium", true).apply()
                }
            }
        }
    }
}
