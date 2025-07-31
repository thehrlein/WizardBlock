package com.tobiashehrlein.tobiswizardblock.entities.game.result

import java.io.Serializable

sealed class TrumpType : Serializable {

    sealed class Selected : TrumpType() {

        object None : Selected()
        object Blue : Selected()
        object Red : Selected()
        object Green : Selected()
        object Yellow : Selected()
    }

    object Unselected : TrumpType()
}
