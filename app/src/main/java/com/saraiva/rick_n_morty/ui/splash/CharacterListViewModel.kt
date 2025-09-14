package com.saraiva.rick_n_morty.ui.splash

import androidx.lifecycle.ViewModel
import com.saraiva.rick_n_morty.domain.usecase.GetCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {
}