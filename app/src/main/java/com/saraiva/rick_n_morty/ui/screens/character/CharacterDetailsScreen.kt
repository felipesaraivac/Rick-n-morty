package com.saraiva.rick_n_morty.ui.screens.character

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saraiva.rick_n_morty.R
import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.data.model.Episode
import com.saraiva.rick_n_morty.ui.components.DefaultTopBar
import com.saraiva.rick_n_morty.ui.components.FullPageLoadingIndicator
import com.saraiva.rick_n_morty.ui.components.InfoTable
import com.saraiva.rick_n_morty.ui.theme.sizing
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailsScreen(
    navController: NavHostController,
    viewModel: CharacterDetailsViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        DefaultTopBar("Rick N Morty") {
            navController.popBackStack()
        }
    }){ paddingValues ->
        val finalState = state.value
        when {
            !finalState.isLoading
                    && finalState.character != null -> {
                CharacterSection(
                    modifier = Modifier.padding(paddingValues),
                    character = finalState.character,
                    episodes = finalState.episodes
                )
            }

            state.value.isLoading -> FullPageLoadingIndicator()
        }

    }
}

@Composable
fun CharacterSection(modifier: Modifier, character: Character, episodes: List<Episode>) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(sizing.spacingS),
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            border = BorderStroke(
                sizing.borderWidth, MaterialTheme.colorScheme.onSecondaryContainer
            ),
        ) {

            CharacterHeader(
                modifier = Modifier, character = character
            )

            InfoTable(
                modifier = Modifier, list = listOf(
                    stringResource(R.string.origin) to character.origin.name,
                    stringResource(R.string.status) to character.status
                )
            )

            EpisodeList(
                modifier = Modifier, episodes = episodes
            )
        }
    }
}

@Composable
fun CharacterHeader(
    modifier: Modifier = Modifier,
    character: Character,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(sizing.headerHeight)
            .padding(top = sizing.spacingM),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(character.image).crossfade(true)
                .build(),
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(sizing.profilePictureSize)
                .clip(CircleShape)
                .border(
                    sizing.borderWidth, MaterialTheme.colorScheme.onSecondaryContainer, CircleShape
                )
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
        )

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = character.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.W700,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = character.species,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.W600,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }


    }
}

@Composable
fun EpisodeList(
    modifier: Modifier = Modifier, episodes: List<Episode>
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(sizing.spacingM),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.episodes).uppercase(Locale.getDefault()),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.W700,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = sizing.spacingS)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(sizing.spacingS),
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(episodes) { index, episode ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = sizing.spacingXXS),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Card(
                        modifier = Modifier.padding(
                            bottom = sizing.spacingXS,
                            end = sizing.spacingXXS
                        ),
                        border = BorderStroke(
                            sizing.borderWidth, MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                    ) {
                        Box(
                            contentAlignment = Alignment.CenterStart,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "#${episode.id}: ${episode.name}",
                                style = MaterialTheme.typography.bodyMedium,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.W600,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                modifier = Modifier.padding(sizing.spacingM)
                            )
                        }
                    }
                }

            }
        }
    }
}
