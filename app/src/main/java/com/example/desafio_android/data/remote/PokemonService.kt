package com.example.desafio_android.data.remote

import com.example.desafio_android.model.ServerPokemon
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon")
    suspend fun getPokemons(): PokemonResult

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): ServerPokemon

}