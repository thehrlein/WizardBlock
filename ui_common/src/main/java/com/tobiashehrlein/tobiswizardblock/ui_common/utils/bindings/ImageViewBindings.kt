package com.tobiashehrlein.tobiswizardblock.ui_common.utils.bindings

import android.content.res.ColorStateList
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.tobiashehrlein.tobiswizardblock.entities.game.result.TrumpType
import com.tobiashehrlein.tobiswizardblock.ui_common.utils.extensions.getColorRes

@BindingAdapter("selectedTrumpType")
fun ImageView.setSelectedTrump(selectedTrumpType: TrumpType) {
    selectedTrumpType.getColorRes()?.let {
        imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, it))
    }
    isVisible = selectedTrumpType !is TrumpType.Unselected && selectedTrumpType !is TrumpType.Selected.None
}

@BindingAdapter("imageTrumpType", "selectedTrumpType", requireAll = true)
fun ImageView.setNoTrumpSelected(imageTrumpType: TrumpType, selectedTrumpType: TrumpType) {
    imageTrumpType.getColorRes()?.let {
        imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(context, it))
    }

    isVisible =
        selectedTrumpType is TrumpType.Unselected || selectedTrumpType is TrumpType.Selected.None
}
