package com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions

import android.content.Intent
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.NavHostFragment
import com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils.DialogInteractor

fun FragmentManager.dispatchOnDialogResult(requestCode: Int, resultCode: Int, data: Intent?) {
    fragments.forEach loop@{
        when {
            !it.isVisible -> return@loop
            it is NavHostFragment -> it.childFragmentManager.dispatchOnDialogResult(
                requestCode,
                resultCode,
                data
            )
            it is DialogInteractor -> it.onDialogResult(requestCode, resultCode, data)
        }
    }
}

fun FragmentManager.dispatchOnActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    fragments.forEach {
        if (it.isVisible) {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }
}
