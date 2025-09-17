package com.saraiva.rick_n_morty.ui.screens.character

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saraiva.rick_n_morty.data.CharacterRepository
import com.saraiva.rick_n_morty.data.model.Character
import com.saraiva.rick_n_morty.ui.state.ListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val characterId: Int = savedStateHandle["characterId"] ?: 0

    private val _state = MutableStateFlow<CharacterDetailsEffects>(CharacterDetailsEffects.Loading)
    val state: StateFlow<CharacterDetailsEffects> get() = _state

    val characters = mutableStateOf<Character?>(null)
    val listState = mutableStateOf(ListState.IDLE)

    private val page = mutableIntStateOf(1)

    init {
        getCharacters()
    }

    fun getCharacters() = viewModelScope.launch {
        characterRepository.getCharacterById(characterId).collect {
            characters.value = it
            _state.emit(CharacterDetailsEffects.CharacterLoaded(it))
        }
    }
}