package com.tobiashehrlein.tobiswizardblock.presentation.about

import com.tobiashehrlein.tobiswizardblock.presentation.general.SingleLiveEvent

class AboutViewModelImpl : AboutViewModel(){

    override val sendEmail = SingleLiveEvent<Unit>()
    override val openWizard= SingleLiveEvent<Unit>()
    override val openFahrstuhl = SingleLiveEvent<Unit>()
    override val openMovieBase = SingleLiveEvent<Unit>()

    override fun onFabClicked() {
        sendEmail.call()
    }

    override fun onWizardBlockClicked() {
        openWizard.call()
    }

    override fun onFahrstuhlBlockClicked() {
        openFahrstuhl.call()
    }

    override fun onMovieBaseClicked() {
        openMovieBase.call()
    }
}