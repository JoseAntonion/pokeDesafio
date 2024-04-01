package com.example.desafio_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.room.Room
import com.example.desafio_android.data.PokemonRepository
import com.example.desafio_android.data.local.LocalDataSource
import com.example.desafio_android.data.local.PokemonDataBase
import com.example.desafio_android.data.remote.RemoteDataSource
import com.example.desafio_android.ui.screens.home.HomeScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            PokemonDataBase::class.java,
            "pokemons"
        ).build()

        val repository = PokemonRepository(
            localDataSource = LocalDataSource(db.pokemonsDao()),
            remoteDataSource = RemoteDataSource()
        )

        setContent {
            HomeScreen(repository)
        }
    }
}