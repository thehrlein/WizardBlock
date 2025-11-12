package com.tobiashehrlein.tobiswizardblock.feature.block

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.tobiashehrlein.tobiswizardblock.core.entities.general.ToolbarButtonType

class GameBlockActivity : BaseGameBlockActivity() {

    override var toolbarButtonType: ToolbarButtonType = ToolbarButtonType.None

    companion object {
        fun start(activity: AppCompatActivity, gameId: Long) {
            activity.startActivity(
                Intent(activity, GameBlockActivity::class.java).apply {
                    putExtra(EXTRA_GAME_ID, gameId)
                }
            )
            activity.finishAffinity()
        }
    }
}
