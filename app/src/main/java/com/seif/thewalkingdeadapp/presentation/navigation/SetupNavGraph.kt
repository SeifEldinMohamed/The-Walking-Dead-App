package com.seif.thewalkingdeadapp.presentation.navigation

import WelcomeScreen
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.seif.thewalkingdeadapp.presentation.screens.home.HomeScreen
import com.seif.thewalkingdeadapp.presentation.screens.splash.SplashScreen
import com.seif.thewalkingdeadapp.presentation.screens.welcome.WelcomeViewModel
import com.seif.thewalkingdeadapp.utils.Constants.DETAILS_ARGUMENT_KEY

@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalPagerApi
@Composable
fun SetupNavGraph(navController: NavHostController) { // we will pass this navHostController form mainActivity
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = { onBoardingCompleted ->
                    navController.popBackStack() // to remove splash screen from backStack
                    if (onBoardingCompleted)
                        navController.navigate(Screen.Home.route)
                    else
                        navController.navigate(Screen.Welcome.route)
                }
            )
        }
        composable(route = Screen.Welcome.route) {
            val welcomeViewModel: WelcomeViewModel = hiltViewModel()
            WelcomeScreen(
                onFinishButtonClicked = {
                    welcomeViewModel.saveOnBoardingState(completed = true)
                    navController.popBackStack()
                    navController.navigate(Screen.Home.route)
                }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(
                onCharacterClicked = { characterId ->
                    navController.navigate(Screen.Details.passCharacterId(characterId = characterId))
                }
            )
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY) {
                type = NavType.IntType
            })
        ) {

        }
        composable(route = Screen.Search.route) {

        }
    }
}