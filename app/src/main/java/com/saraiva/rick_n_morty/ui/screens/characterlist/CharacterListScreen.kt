package com.saraiva.rick_n_morty.ui.screens.characterlist

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.ui.components.FullPageLoadingIndicator
import com.saraiva.rick_n_morty.ui.components.InfiniteListHandler
import com.saraiva.rick_n_morty.ui.navigation.Screen
import com.saraiva.rick_n_morty.ui.theme.sizing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CharacterListScreen(
    navHostController: NavHostController, viewModel: CharacterListViewModel
) {

    val viewState = viewModel.state.collectAsStateWithLifecycle()
    val characters = viewModel.characters

    val onClickListener: (Character) -> Unit = { character: Character ->
        CoroutineScope(Dispatchers.Default).launch {
            viewModel.processEvent(CharacterListEvents.OnCharacterClick(character))
        }
    }

    Scaffold { paddingValues ->
        when (viewState.value) {
            CharacterListEffects.EndPagination -> CharacterList(
                modifier = Modifier.padding(paddingValues),
                characters = characters,
                onEndReached = {
                    CoroutineScope(Dispatchers.Default).launch {
                        viewModel.processEvent(CharacterListEvents.LoadMoreCharacters)
                    }
                },
                onCharacterClick = onClickListener
            )

            CharacterListEffects.Error -> TODO()
            CharacterListEffects.Loading -> FullPageLoadingIndicator()
            is CharacterListEffects.OpenCharacterDetail -> {
                val effect = viewState.value as CharacterListEffects.OpenCharacterDetail
                navHostController.navigate(Screen.CharacterScreen.createRoute(effect.character.id))
                viewModel.resetState()
                FullPageLoadingIndicator()
            }

            CharacterListEffects.Paginating -> CharacterList(
                modifier = Modifier.padding(
                    paddingValues
                ), characters = characters,
                paginating = true,
                onCharacterClick = onClickListener
            )
        }
    }
}

@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onEndReached: () -> Unit = {},
    onCharacterClick: (Character) -> Unit = {},
    paginating: Boolean = false
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(sizing.minCardWidth),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.spacedBy(sizing.spacingXS),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(sizing.spacingS),
        state = lazyGridState,
    ) {
        items(characters.size) {
            CharacterItem(
                modifier = Modifier.padding(
                    bottom = sizing.spacingXS,
                    start = sizing.spacingXXS,
                    end = sizing.spacingXXS
                ),
                character = characters[it],
                onClickListener = { onCharacterClick(characters[it]) }
            )
        }
    }

    InfiniteListHandler(paginating, lazyGridState) {
        onEndReached()
    }
}

@Composable
fun CharacterItem(
    modifier: Modifier, character: Character,
    onClickListener: () -> Unit = {}
) {
    Card(
        modifier = modifier.padding(sizing.borderWidth),
        border = BorderStroke(sizing.borderWidth, MaterialTheme.colorScheme.onSecondaryContainer),
        onClick = onClickListener
    ) {
        Column(
            modifier = Modifier
                .aspectRatio(9 / 16f)
                .background(
                    MaterialTheme.colorScheme.background, RoundedCornerShape(sizing.spacingM)
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
                        .border(sizing.borderWidth, MaterialTheme.colorScheme.onSecondaryContainer)
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
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.W600,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = sizing.spacingM,
                            end = sizing.spacingM,
                            top = sizing.spacingXXS,
                            bottom = sizing.spacingXS
                        )
                )
            }
        }
    }

}