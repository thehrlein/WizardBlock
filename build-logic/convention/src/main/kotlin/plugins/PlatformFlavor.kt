package plugins

import models.Flavor

data class PlatformFlavor(
    override val name: String,
    val applicationId: String,
) : Flavor
