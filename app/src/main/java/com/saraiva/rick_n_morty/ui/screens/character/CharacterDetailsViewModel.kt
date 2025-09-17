package com.saraiva.rick_n_morty.ui.screens.character


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saraiva.rick_n_morty.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val characterId: Int = savedStateHandle["characterId"] ?: 0

    private val _state = MutableStateFlow(CharacterDetailsState.initial())
    val state: StateFlow<CharacterDetailsState> get() = _state

    init {
        viewModelScope.launch {
            if (characterId == 0) {
                _state.emit(CharacterDetailsState.initial().copy(isError = true, isLoading = false))
            } else {
                processEvent(CharacterDetailsEvents.LoadCharacter)
            }
        }
    }

    fun processEvent(event: CharacterDetailsEvents) = viewModelScope.launch {
        when (event) {
            is CharacterDetailsEvents.LoadCharacter -> {
                getCharacter()
            }

            is CharacterDetailsEvents.LoadEpisodes -> {
                getEpisodes(event.character.episode.map { it.split("/").last().toInt() })
            }
        }
    }

    fun getCharacter() {
        viewModelScope.launch {
            characterRepository.getCharacterById(characterId).collect { character ->
                val viewState = _state.value.copy(
                    character = character,
                    episodes = emptyList(),
                    isLoading = true,
                    isError = false
                )
                processEvent(CharacterDetailsEvents.LoadEpisodes(character))
                _state.emit(viewState)
            }
        }
    }

    fun getEpisodes(ids: List<Int>) {
        viewModelScope.launch {
            characterRepository.getEpisodeList(ids).collect { episodes ->
                val viewState = _state.value.copy(
                    character = _state.value.character,
                    episodes = episodes,
                    isLoading = false,
                    isError = false
                )
                _state.emit(viewState)
            }
        }
    }
}