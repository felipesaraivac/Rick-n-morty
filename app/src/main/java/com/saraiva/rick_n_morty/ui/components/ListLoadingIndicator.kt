package com.saraiva.rick_n_morty.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.saraiva.rick_n_morty.R
import com.saraiva.rick_n_morty.ui.theme.sizing

@Composable
fun ListLoadingIndicator(isLoading: Boolean) {

    val density = LocalDensity.current

    AnimatedVisibility(
        visible = isLoading,
        enter = slideInVertically(
            initialOffsetY = { with(density) { 200.dp.roundToPx() } }, // Appears from 200.dp below
            animationSpec = tween(durationMillis = 500)
        ),
        exit = slideOutVertically(
            targetOffsetY = { with(density) { 200.dp.roundToPx() } }, // Disappears 200.dp below
            animationSpec = tween(durationMillis = 500)
        )
        // You could also use fadeOut() for a different exit animation:
        // exit = fadeOut(animationSpec = tween(durationMillis = 300))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            DotLottieAnimation(
                source = DotLottieSource.Res(R.raw.loading_searching),
                autoplay = true,
                loop = true,
                useFrameInterpolation = true
            )
        }
    }
}