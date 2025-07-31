package com.tobiashehrlein.tobiswizardblock.core.repositories.datasource.sharedpref

import android.content.Context
import com.tobiashehrlein.tobiswizardblock.core.interactor.datasource.sharedpref.UserSettingsPersistence

class WizardBlockSharedPreferences(
    context: Context
) : SharedPreferenceDelegates(context), UserSettingsPersistence {

    override val preferencesFileName: String = "WizardBlockAppPreferences"

    override var isShowTrumpDialogEnabled by BooleanPrefDelegate(
        UserSettingsPersistence.KEY_IS_SHOW_TRUMP_DIALOG_ENABLED,
        UserSettingsPersistence.DEFAULT_IS_SHOW_TRUMP_DIALOG_ENABLED
    )
}
