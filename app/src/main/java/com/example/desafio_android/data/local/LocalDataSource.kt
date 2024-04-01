package com.example.desafio_android.data.local

import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val dao: PokemonDao) {

    val pokemons: Flow<List<LocalPokemon>> = dao.getPokemons()

    suspend fun insertAll(pokemons: List<LocalPokemon>) {
        dao.insertAll(pokemons)
    }

    suspend fun count(): Int {
        return dao.count()
    }

    suspend fun updatePokemon(pokemon: LocalPokemon) {
        dao.updatePokemon(pokemon)
    }

}