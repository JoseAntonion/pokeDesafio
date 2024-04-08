package com.example.desafio_android.data.remote

import com.example.desafio_android.model.ServerPokemon

class PokemonResult(
    val count: Int,
    val next: String,
    val previous: String?,
    val results: List<ServerPokemon>
)


