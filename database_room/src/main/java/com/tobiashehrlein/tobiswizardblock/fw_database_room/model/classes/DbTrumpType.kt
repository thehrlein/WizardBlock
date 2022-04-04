package com.tobiashehrlein.tobiswizardblock.fw_database_room.model.classes

sealed class DbTrumpType {

    sealed class Selected : DbTrumpType() {
        object None : Selected()
        object Blue : Selected()
        object Red : Selected()
        object Green : Selected()
        object Yellow : Selected()
    }

    object Unselected : DbTrumpType()

    fun getQualifier(): Int {
        return when (this) {
            is Unselected -> 0
            is Selected.None -> 1
            is Selected.Blue -> 2
            is Selected.Red -> 3
            is Selected.Green -> 4
            is Selected.Yellow -> 5
        }
    }

    companion object {
        fun getClass(identifier: Int): DbTrumpType {
            return when (identifier) {
                1 -> Selected.None
                2 -> Selected.Blue
                3 -> Selected.Red
                4 -> Selected.Green
                5 -> Selected.Yellow
                else -> Unselected
            }
        }
    }

}
