package com.saraiva.rick_n_morty.ui.screens.character

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.ui.components.FullPageLoadingIndicator
import com.saraiva.rick_n_morty.ui.theme.sizing

@Composable
fun CharacterDetailsScreen(
    navHostController: NavHostController,
    viewModel: CharacterDetailsViewModel
) {
    val state = viewModel.state.collectAsStateWithLifecycle()

    Scaffold { paddingValues ->
        when (state.value) {
            is CharacterDetailsEffects.CharacterLoaded -> {
                val character = (state.value as CharacterDetailsEffects.CharacterLoaded).character
                CharacterSection(
                    modifier = Modifier.padding(
                        paddingValues
                    ),
                    character = character
                )
            }

            CharacterDetailsEffects.Error -> TODO()
            CharacterDetailsEffects.Loading -> FullPageLoadingIndicator()
        }

    }
}

@Composable
fun CharacterSection(modifier: Modifier, character: Character) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(sizing.spacingS),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSecondaryContainer),
        ) {

            CharacterHeader(
                modifier = Modifier,
                character = character
            )

            TableInfo(
                modifier = Modifier,
                list = listOf(
                    "origin" to character.origin.name,
                    "status" to character.status
                )
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
            model = ImageRequest.Builder(LocalContext.current)
                .data(character.image)
                .crossfade(true)
                .build(),
            contentDescription = character.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(sizing.profilePictureSize)
                .clip(CircleShape)
                .border(1.dp, MaterialTheme.colorScheme.onSecondaryContainer, CircleShape)
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
fun TableInfo(
    modifier: Modifier = Modifier,
    list: List<Pair<String, String>> = emptyList()
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(sizing.spacingM),
        border = BorderStroke(sizing.borderWidth, MaterialTheme.colorScheme.onSecondaryContainer)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(list) {
                Row {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(.2f)
                            .padding(sizing.spacingS),
                        horizontalAlignment = Alignment.End,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = it.first,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.W600,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                    }
                    Column(
                        modifier = Modifier
                            .padding(sizing.spacingS),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = it.second,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.W400,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
            }
        }
    }
}