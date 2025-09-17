package com.saraiva.rick_n_morty.data.datasource

import com.saraiva.rick_n_morty.data.model.Character

class InMemoryDataSource {

    private val characters = mutableMapOf<Int, Character>()

    fun addCharacter(character: Character) {
        characters.put(character.id, character)
    }

    fun getAllCharacters(): List<Character> {
        return characters.values.sortedBy { it.id }
    }

    fun getCharacterById(id: Int): Character? = characters[id]

    fun clear() {
        characters.clear()
    }
}