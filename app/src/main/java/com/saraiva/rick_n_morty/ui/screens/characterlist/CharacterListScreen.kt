package com.saraiva.rick_n_morty.ui.screens.characterlist

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.crossfade
import coil3.request.placeholder
import com.lottiefiles.dotlottie.core.compose.ui.DotLottieAnimation
import com.lottiefiles.dotlottie.core.util.DotLottieSource
import com.saraiva.rick_n_morty.R
import com.saraiva.rick_n_morty.domain.model.Character
import com.saraiva.rick_n_morty.ui.components.FullPageLoadingIndicator
import com.saraiva.rick_n_morty.ui.state.ListState
import com.saraiva.rick_n_morty.ui.theme.sizing

@Composable
fun CharacterListScreen(
    navHostController: NavHostController,
    viewModel: CharacterListViewModel
) {
    Scaffold { paddingValues ->
        val list = viewModel.characters
        val listState = viewModel.listState
        when (listState.value) {
            ListState.IDLE -> {
                CharacterList(modifier = Modifier.padding(paddingValues), characters = list)
            }

            ListState.LOADING -> {
                FullPageLoadingIndicator()
            }

            ListState.PAGINATING -> {

                CharacterList(characters = list)

            }

            else -> {}
        }
    }
}

@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<Character>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(8.dp),
        state = rememberLazyGridState()
    ) {
        items(characters.size) {
            CharacterItem(
                modifier = Modifier.padding(
                    bottom = sizing.spacingXS,
                    start = sizing.spacingXXS,
                    end = sizing.spacingXXS
                ),
                character = characters[it]
            )
        }
    }
}

@Composable
fun CharacterItem(
    modifier: Modifier,
    character: Character
) {
    Card(
        modifier = modifier,
        border = BorderStroke(sizing.spacingXXS, MaterialTheme.colorScheme.primaryContainer),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .aspectRatio(9 / 16f)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    RoundedCornerShape(sizing.spacingM)
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.8f)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(character.image)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    contentDescription = character.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(
                            RoundedCornerShape(
                                topStart = sizing.spacingS,
                                topEnd = sizing.spacingS,
                                bottomEnd = sizing.spacingXS,
                                bottomStart = sizing.spacingXS
                            )
                        )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = sizing.spacingM, end = sizing.spacingM, top = sizing.spacingS, bottom = sizing.spacingXS)
                )
            }
        }
    }

}