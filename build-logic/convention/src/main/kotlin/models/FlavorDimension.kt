package models

interface FlavorDimension<T : Flavor> {
    val name: String
    val productFlavors: Set<Flavor>
}
