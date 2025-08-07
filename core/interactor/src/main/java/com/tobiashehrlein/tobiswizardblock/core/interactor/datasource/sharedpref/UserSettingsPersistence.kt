package com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.sharedpref

interface UserSettingsPersistence {

    companion object {
        const val DEFAULT_IS_SHOW_TRUMP_DIALOG_ENABLED = true
        const val KEY_IS_SHOW_TRUMP_DIALOG_ENABLED = "key.is_show_trump_dialog_enabled"
    }

    var isShowTrumpDialogEnabled: Boolean
}
