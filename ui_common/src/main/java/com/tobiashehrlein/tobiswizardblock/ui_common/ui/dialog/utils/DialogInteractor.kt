package com.tobiashehrlein.tobiswizardblock.ui_common.ui.dialog.utils

import android.content.Intent

interface DialogInteractor {

    fun onDialogResult(requestCode: Int, resultCode: Int, data: Intent?)
}
