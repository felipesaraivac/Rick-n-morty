package com.saraiva.rick_n_morty.ui.navigation

sealed class Screen(val route: String) {
    object SplashScreen: Screen("splash" )
    object CharacterListScreen: Screen("characters" )
    object CharacterScreen: Screen("character/{id}" )
}