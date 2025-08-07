package com.tobiashehrlein.tobiswizardblock.core.databaseroom.model.classes

private const val QUALIFIER_UNSELECTED = 0
private const val QUALIFIER_SELECTED_NONE = 1
private const val QUALIFIER_SELECTED_BLUE = 2
private const val QUALIFIER_SELECTED_RED = 3
private const val QUALIFIER_SELECTED_GREEN = 4
private const val QUALIFIER_SELECTED_YELLOW = 5

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
            is Unselected -> QUALIFIER_UNSELECTED
            is Selected.None -> QUALIFIER_SELECTED_NONE
            is Selected.Blue -> QUALIFIER_SELECTED_BLUE
            is Selected.Red -> QUALIFIER_SELECTED_RED
            is Selected.Green -> QUALIFIER_SELECTED_GREEN
            is Selected.Yellow -> QUALIFIER_SELECTED_YELLOW
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
