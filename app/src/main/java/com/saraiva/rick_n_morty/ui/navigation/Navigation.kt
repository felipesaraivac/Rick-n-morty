package com.saraiva.felipe.foodcompendium.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.saraiva.rick_n_morty.ui.characterlist.CharacterListScreen
import com.saraiva.rick_n_morty.ui.navigation.Screen
import com.saraiva.rick_n_morty.ui.splash.SplashScreen
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds
import androidx.hilt.navigation.compose.hiltViewModel
import com.saraiva.rick_n_morty.ui.characterlist.CharacterListViewModel

@Composable
fun SetupNavController(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.SplashScreen.route) {
        composable(route = Screen.SplashScreen.route) {
            SplashScreen(navController, )
            LaunchedEffect(Unit) {
                delay(5.seconds)
                navController.navigate(route = Screen.CharacterScreen.route)
            }
        }
        composable(route = Screen.CharacterScreen.route) {
            CharacterListScreen(navHostController = navController, hiltViewModel<CharacterListViewModel>())
        }
//        composable(route = Screen.QRScreen.route) {
//            QrScreen(navHostController = navController, mainViewModel = viewModel)
//        }
//        composable(route = Screen.MenuScreen.route) {
//
//        }
    }
}