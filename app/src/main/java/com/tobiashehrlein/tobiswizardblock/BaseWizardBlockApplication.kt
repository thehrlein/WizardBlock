package com.tobiashehrlein.tobiswizardblock

import android.app.Application
import com.google.firebase.FirebaseApp
import com.tobiashehrlein.tobiswizardblock.koin.Koin
import com.tobiashehrlein.tobiswizardblock.koin.KoinLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

abstract class BaseWizardBlockApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initFirebase()
        initKoin()
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initKoin() {
        startKoin {
            logger(KoinLogger())
            androidLogger(Level.ERROR)
            androidContext(this@BaseWizardBlockApplication)
            modules(Koin.modules)
        }
    }
}