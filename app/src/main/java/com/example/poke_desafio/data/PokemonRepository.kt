package com.example.desafio_android.data

import com.example.desafio_android.data.local.LocalDataSource
import com.example.desafio_android.data.local.LocalPokemon
import com.example.desafio_android.data.remote.RemoteDataSource
import com.example.desafio_android.model.ServerPokemon
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    val pokemons: Flow<List<LocalPokemon>> = localDataSource.pokemons

    suspend fun updatePokemon(pokemon: LocalPokemon) {
        localDataSource.updatePokemon(pokemon)
    }

    suspend fun getPokemons() {
        val isDbEmpty = localDataSource.count() == 0
        if (isDbEmpty) {
            localDataSource.insertAll(remoteDataSource.getPokemons())
        }
    }

    suspend fun getPokemon(name: String): ServerPokemon {
        return remoteDataSource.getPokemon(name)
    }

}