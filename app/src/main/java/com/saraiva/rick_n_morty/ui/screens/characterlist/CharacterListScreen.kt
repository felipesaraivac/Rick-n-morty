package com.saraiva.rick_n_morty.ui.screens.characterlist

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.background
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.ui.components.DefaultTopBar
import com.saraiva.rick_n_morty.ui.components.FullPageLoadingIndicator
import com.saraiva.rick_n_morty.ui.components.InfiniteListHandler
import com.saraiva.rick_n_morty.ui.navigation.Screen
import com.saraiva.rick_n_morty.ui.theme.sizing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterListScreen(
    navHostController: NavHostController,
    viewModel: CharacterListViewModel = hiltViewModel(),
    context: Context = LocalContext.current
) {

    val viewState = viewModel.state.collectAsStateWithLifecycle()

    val effects = viewModel.effect.collectAsStateWithLifecycle(null)

    LaunchedEffect(effects.value) {
        if (effects.value is CharacterListEffects.Backpress) (context as Activity).finish()
        val characterId = (effects.value as? CharacterListEffects.OpenCharacterDetail)?.character
            ?: return@LaunchedEffect
        navHostController.navigate(Screen.CharacterScreen.createRoute(characterId))
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        topBar = {
            DefaultTopBar(title = "Rick N Morty", scrollBehavior = scrollBehavior) {
                viewModel.processEvent(CharacterListEvents.OnBackpress)
            }
        }
    ) { paddingValues ->
        val state = viewState.value
        when {
            !state.isLoading -> CharacterList(
                modifier = Modifier
                    .padding(paddingValues)
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                characters = state.characters,
                onEndReached = {
                    CoroutineScope(Dispatchers.Default).launch {
                        viewModel.processEvent(CharacterListEvents.LoadMoreCharacters)
                    }
                },
                paginating = state.isPaginating,
                onCharacterClick = { character ->
                    viewModel.processEvent(CharacterListEvents.OnCharacterClick(character))
                }
            )

            state.isLoading -> FullPageLoadingIndicator()

        }
    }
}

@Composable
fun CharacterList(
    modifier: Modifier = Modifier,
    characters: List<Character>,
    onEndReached: () -> Unit = {},
    onCharacterClick: (Character) -> Unit = {},
    paginating: Boolean = false,
    hasMore: Boolean = true,
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(sizing.minCardWidth),
        horizontalArrangement = Arrangement.spacedBy(sizing.spacingXS),
        verticalArrangement = Arrangement.spacedBy(sizing.spacingXS),
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(sizing.spacingS),
        state = lazyGridState,
    ) {
        items(characters.size) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = sizing.spacingXXS, end = sizing.spacingXXS),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                shape = MaterialTheme.shapes.medium
            ) {
                CharacterItem(
                    modifier = Modifier.padding(
                        bottom = sizing.spacingXXS,
                        end = sizing.spacingXXS
                    ),
                    character = characters[it],
                    onClickListener = { onCharacterClick(characters[it]) }
                )
            }
        }
    }

    if (hasMore) {
        InfiniteListHandler(paginating, lazyGridState) {
            onEndReached()
        }
    }
}

@Composable
fun CharacterItem(
    modifier: Modifier, character: Character,
    onClickListener: () -> Unit = {}
) {
    Card(
        modifier = modifier.padding(sizing.borderWidth),
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
                    .fillMaxHeight(.75f)
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
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                thickness = sizing.spacingXXS
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.W700,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = sizing.spacingM,
                            end = sizing.spacingM,
                            top = sizing.spacingXXS,
                            bottom = sizing.spacingXXS
                        )
                )
            }
        }
    }

}