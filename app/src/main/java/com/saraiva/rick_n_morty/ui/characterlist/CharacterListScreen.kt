package com.saraiva.rick_n_morty.ui.characterlist

import android.content.res.Resources
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.saraiva.rick_n_morty.domain.model.Character
import com.saraiva.rick_n_morty.ui.theme.LocalSizing
import com.saraiva.rick_n_morty.ui.theme.sizing

@Composable
fun CharacterListScreen(
    navHostController: NavHostController,
    viewModel: CharacterListViewModel
) {
    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}

@Composable
@Preview
fun CharacterItem() {
    Card(
        elevation = CardDefaults.cardElevation()
    ) {

        Column(
            modifier = Modifier
                .size(50.dp, 70.dp)
                .aspectRatio(16 / 9f)
                .background(
                    MaterialTheme.colorScheme.background,
                    RoundedCornerShape(sizing.spacingM)
                )
        ) {
            Text(
                text = "Character Name",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(sizing.spacingM)
            )
        }
    }
}