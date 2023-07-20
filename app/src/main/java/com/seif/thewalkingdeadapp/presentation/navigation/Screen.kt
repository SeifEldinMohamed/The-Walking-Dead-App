package com.seif.thewalkingdeadapp.presentation.navigation

import com.seif.thewalkingdeadapp.utils.Constants.DETAILS_ARGUMENT_KEY

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Welcome : Screen("welcome_screen")
    object Home : Screen("home_screen")
    object Details : Screen("details_screen/{${DETAILS_ARGUMENT_KEY}}") {
        fun passCharacterId(characterId: Int): String {
            return "details_screen/$characterId"
        }
    }

    object Search : Screen("search_screen")
}