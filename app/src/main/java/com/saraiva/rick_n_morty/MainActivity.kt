package com.saraiva.rick_n_morty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.saraiva.felipe.foodcompendium.presentation.navigation.SetupNavController
import com.saraiva.rick_n_morty.ui.theme.RNMTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RNMTheme {
                val navHostController = rememberNavController()
                SetupNavController(navHostController)
            }
        }
    }
}