package com.saraiva.rick_n_morty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.rememberNavController
import com.saraiva.rick_n_morty.ui.navigation.SetupNavController
import com.saraiva.rick_n_morty.ui.theme.RNMTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            RNMTheme {
                val isLight =  !isSystemInDarkTheme()
                SideEffect {
                    WindowInsetsControllerCompat(window, window.decorView).isAppearanceLightStatusBars = isLight
                }

                val navHostController = rememberNavController()
                SetupNavController(navHostController)
            }
        }
    }
}