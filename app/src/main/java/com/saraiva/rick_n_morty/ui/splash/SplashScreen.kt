package com.saraiva.rick_n_morty.ui.splash

import android.annotation.SuppressLint
import android.window.SplashScreenView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavHostController

@Composable
fun SplashScreen(
    navHostController: NavHostController
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(
                    50.dp
                )
            )
        }
    }
}