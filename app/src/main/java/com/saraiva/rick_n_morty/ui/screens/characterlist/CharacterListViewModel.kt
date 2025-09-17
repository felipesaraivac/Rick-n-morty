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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _state = MutableStateFlow<CharacterListEffects>(CharacterListEffects.Loading)
    val state: StateFlow<CharacterListEffects> get() = _state

    private val _characters = mutableStateListOf<Character>()
    val characters: List<Character> get() = _characters

    private val listState = mutableStateOf(ListState.IDLE)
    private val page = mutableIntStateOf(1)
    private val hasMore = mutableStateOf(true)

    init {
        viewModelScope.launch {
            processEvent(CharacterListEvents.LoadCharacters)
        }
    }

    suspend fun processEvent(event: CharacterListEvents) {
        when (event) {
            CharacterListEvents.LoadCharacters -> {
                loadCharacters()
                _state.emit(CharacterListEffects.Loading)
            }

            CharacterListEvents.LoadMoreCharacters -> {
                loadCharacters()
                _state.emit(CharacterListEffects.Paginating)
            }

            is CharacterListEvents.OnCharacterClick -> {
                _state.emit(CharacterListEffects.OpenCharacterDetail(event.character))
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
                _state.emit(CharacterListEffects.EndPagination)
            }
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _state.emit(CharacterListEffects.EndPagination)
        }
    }
}