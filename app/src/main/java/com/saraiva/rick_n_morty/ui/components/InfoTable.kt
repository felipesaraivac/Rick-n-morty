package com.saraiva.rick_n_morty.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.saraiva.rick_n_morty.ui.theme.sizing
import java.util.Locale

@Composable
fun InfoTable(
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
            itemsIndexed(list) { index, it ->
                if (index != 0) {
                    HorizontalDivider(
                        modifier = Modifier.padding(
                            start = sizing.spacingM,
                            end = sizing.spacingM
                        )
                    )
                }
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
                            text = it.second.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
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