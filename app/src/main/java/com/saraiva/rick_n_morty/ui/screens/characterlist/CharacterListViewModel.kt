package com.saraiva.rick_n_morty.ui.screens.characterlist

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
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
) : ViewModel() {

    private var _state = MutableStateFlow(CharacterListState.initial())
    val state: StateFlow<CharacterListState> get() = _state

    val effect = MutableSharedFlow<CharacterListEffects>()
//    val effect: StateFlow<CharacterListEffects> get() = _effect

    private val _characters = mutableStateListOf<Character>()

    private val listState = mutableStateOf(ListState.IDLE)
    private val page = mutableIntStateOf(1)
    private val hasMore = mutableStateOf(true)

    init {
        viewModelScope.launch {
            processEvent(CharacterListEvents.LoadCharacters)
        }
    }

    fun processEvent(event: CharacterListEvents) {
        viewModelScope.launch {
            when (event) {
                CharacterListEvents.LoadCharacters -> {
                    loadCharacters()
                    _state.emit(_state.value.copy(isLoading = true))
                }

                CharacterListEvents.LoadMoreCharacters -> {
                    loadCharacters()
                    _state.emit(_state.value.copy(isPaginating = true))
                }

                is CharacterListEvents.OnCharacterClick -> {
                    effect.emit(CharacterListEffects.OpenCharacterDetail(event.character.id))
                }

                CharacterListEvents.ResetState -> {
                    resetState()
                }
            }
        }
    }

    private fun loadCharacters() = viewModelScope.launch {
        if (listState.value == ListState.IDLE && hasMore.value) {
            if (page.intValue == 1) {
                listState.value = ListState.LOADING
            } else {
                listState.value = ListState.PAGINATING
            }

            characterRepository.getCharacters(page.intValue).collect { pageData ->
                if (page.intValue == 1) {
                    _characters.clear()
                }
                _characters.addAll(pageData.data)
                page.intValue++
                listState.value = ListState.IDLE
                hasMore.value = pageData.hasMore
                _state.emit(
                    _state.value
                        .copy(
                            _characters,
                            isLoading = false,
                            isPaginating = false,
                            hasMore = hasMore.value
                        )
                )
            }
        }
    }

    fun resetState() = viewModelScope.launch {
        _state.emit(_state.value.copy(isLoading = false, isPaginating = false, isError = false))
    }
}