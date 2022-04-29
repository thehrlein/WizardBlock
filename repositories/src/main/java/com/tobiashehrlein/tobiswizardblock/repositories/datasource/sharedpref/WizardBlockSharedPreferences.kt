package com.tobiashehrlein.tobiswizardblock.repositories.datasource.sharedpref

import android.content.Context
import com.tobiashehrlein.tobiswizardblock.interactor.datasource.sharedpref.UserSettingsPersistence

class WizardBlockSharedPreferences(
    context: Context
) : SharedPreferenceDelegates(context), UserSettingsPersistence {

    override val preferencesFileName: String = "WizardBlockAppPreferences"

    override var isShowTrumpDialogEnabled by BooleanPrefDelegate(
        UserSettingsPersistence.KEY_IS_SHOW_TRUMP_DIALOG_ENABLED,
        UserSettingsPersistence.DEFAULT_IS_SHOW_TRUMP_DIALOG_ENABLED
    )
}
