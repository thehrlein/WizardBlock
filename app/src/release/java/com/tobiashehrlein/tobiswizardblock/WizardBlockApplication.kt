package com.tobiashehrlein.tobiswizardblock

import android.app.Application
import com.google.firebase.FirebaseApp
import com.tobiashehrlein.tobiswizardblock.koin.Koin
import com.tobiashehrlein.tobiswizardblock.koin.KoinLogger
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class WizardBlockApplication : BaseWizardBlockApplication() {

}