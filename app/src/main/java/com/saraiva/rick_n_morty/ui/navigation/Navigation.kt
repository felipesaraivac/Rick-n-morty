package com.saraiva.rick_n_morty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.saraiva.rick_n_morty.ui.screens.character.CharacterDetailsScreen
import com.saraiva.rick_n_morty.ui.screens.characterlist.CharacterListScreen
import com.saraiva.rick_n_morty.ui.screens.splash.SplashScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

@Composable
fun SetupNavController(navController: NavHostController) {
    val deeplinkUri = "rnm://"
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController)
            LaunchedEffect(Unit) {
                delay(1.seconds)
                navController.navigate(route = Screen.CharacterListScreen.route)
            }
        }
        composable(
            route = Screen.CharacterListScreen.route,
            deepLinks = listOf(navDeepLink<Unit>(basePath = "${deeplinkUri}characters"))
        ) {
            CharacterListScreen(
                navHostController = navController,
            )
        }

        composable(
            route = Screen.CharacterScreen.route,
            arguments = listOf(navArgument("characterId") { type = NavType.IntType }),
            deepLinks = listOf(navDeepLink<Int>(basePath = "${deeplinkUri}character/{characterId}"))
        ) {
            CharacterDetailsScreen()
        }
    }
}