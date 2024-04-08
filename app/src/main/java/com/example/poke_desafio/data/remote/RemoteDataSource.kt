package com.example.desafio_android.data.remote

import com.example.desafio_android.data.local.LocalPokemon
import com.example.desafio_android.model.ServerPokemon
import com.example.desafio_android.model.toLocalPokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    suspend fun getPokemons(): List<LocalPokemon> {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
            .getPokemons()
            .results
            .map { it.toLocalPokemon() }
    }

    suspend fun getPokemon(name: String): ServerPokemon {
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PokemonService::class.java)
            .getPokemon(name)

    }

}
