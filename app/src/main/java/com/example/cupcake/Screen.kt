package com.example.cupcake

enum class Screen {

    START,
    FLAVOR,
    PICKUP,
    SUMMARY
}


sealed class NavigationItem(val route: String) {
    object Start : NavigationItem(Screen.START.name)
    object Flavor : NavigationItem(Screen.FLAVOR.name)
    object PickUp : NavigationItem(Screen.PICKUP.name)
    object Summary : NavigationItem(Screen.SUMMARY.name)

}