package com.saraiva.rick_n_morty.ui.characterlist

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.saraiva.rick_n_morty.domain.model.Character
import com.saraiva.rick_n_morty.ui.state.ListState
import com.saraiva.rick_n_morty.ui.theme.sizing

@Composable
fun CharacterListScreen(
    navHostController: NavHostController,
    viewModel: CharacterListViewModel
) {
    val list = viewModel.characters
    val listState = viewModel.listState
    when (listState.value) {
        ListState.IDLE -> {
            CharacterList(characters = list)
        }
        ListState.LOADING -> {
            Column() {
                Text(text = "Loading...")
            }
        }

        ListState.PAGINATING -> {
            Column() {
                Text(text = "Paginating...")
            }
        }

        else -> {}
    }
}

@Composable
fun CharacterList(
    characters: List<Character>
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(100.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(8.dp),
        state = rememberLazyGridState()
    ) {
        items( characters.size ) {
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
fun CharacterItem(modifier: Modifier, 
                  character: Character) {
    Card(
        modifier = modifier,
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
                    contentDescription = character.name,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            RoundedCornerShape(
                                topStart = sizing.spacingS,
                                topEnd = sizing.spacingS,
                            )
                        )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(sizing.spacingM)
                )
            }
        }
    }
}