package com.saraiva.rick_n_morty.ui.screens.characterlist

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saraiva.rick_n_morty.domain.model.Character
import com.saraiva.rick_n_morty.domain.usecase.GetCharactersUseCase
import com.saraiva.rick_n_morty.ui.state.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val characters = mutableStateListOf<Character>()
    val listState = mutableStateOf(ListState.IDLE)

    private val page = mutableIntStateOf(1)

    init {
        getCharacters()
    }
    fun getCharacters() = viewModelScope.launch {
        if (listState.value == ListState.IDLE) {
            if (page.intValue == 1) {
                listState.value = ListState.LOADING
            } else {
                listState.value = ListState.PAGINATING
            }

            getCharactersUseCase(page.intValue).collect { list ->
                if (page.intValue == 1) {
                    characters.clear()
                }
                characters.addAll(list)

                page.intValue++
                listState.value = ListState.IDLE
            }
        }
    }
}