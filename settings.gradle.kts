rootProject.name = "WizardBlock"

include(":app")

// ui
include(":ui_navigation")
include(":ui_game_settings")
include(":ui_block")
include(":ui_saved_games")
include(":ui_about")
include(":ui_settings")
include(":ui_common")

// framework modules
include(":database_room")
include(":repositories")

// basic architecture modules
include(":presentation")
include(":interactor")
include(":entities")
include(":utils")
