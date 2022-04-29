package com.tobiashehrlein.tobiswizardblock

import timber.log.Timber

class WizardBlockApplication : BaseWizardBlockApplication() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }
}
