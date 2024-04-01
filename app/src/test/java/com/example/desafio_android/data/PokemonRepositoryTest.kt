package com.example.desafio_android.data

import com.example.desafio_android.data.local.LocalDataSource
import com.example.desafio_android.data.local.LocalPokemon
import com.example.desafio_android.data.remote.RemoteDataSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verifyBlocking

class PokemonRepositoryTest {
    @Test
    fun `When DB is empty, server is called`() {
        val localDataSource = mock<LocalDataSource> {
            onBlocking { count() } doReturn 0 // se configura la funcion count() para que retorne 0 = db vacia
        }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        // genera contexto de corrutina para testear
        runBlocking { repository.getPokemons() }

        verifyBlocking(remoteDataSource) { getPokemons() }
    }

    @Test
    fun `when db is empty, pokemons are saved into db`() {
        val expectedPokemons = listOf(
            LocalPokemon(
                id = 1,
                favorite = false,
                name = "pikachu",
                base_experience = 50,
                height = 1,
                order = 1,
                is_default = true,
                location_area_encounters = "",
                weight = 1
            )
        )
        val localDataSource = mock<LocalDataSource> {
            onBlocking { count() } doReturn 0 // se configura la funcion count() para que retorne 0 = db vacia
        }
        val remoteDataSource = mock<RemoteDataSource> {
            onBlocking { getPokemons() } doReturn expectedPokemons
        }
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        // genera contexto de corrutina para testear
        runBlocking { repository.getPokemons() }

        verifyBlocking(localDataSource) { insertAll(any()) }
    }

    @Test
    fun `when db is not empty, remote data source isnt called`() {
        val localDataSource = mock<LocalDataSource> {
            onBlocking { count() } doReturn 1 // se configura la funcion count() para que retorne 0 = db vacia
        }
        val remoteDataSource = mock<RemoteDataSource>()
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        // genera contexto de corrutina para testear
        runBlocking { repository.getPokemons() }

        verifyBlocking(remoteDataSource, times(0)) { getPokemons() }
    }

    fun `when db is not empty, pokemons are recovered from db`(){
        val localPokemons = listOf(
            LocalPokemon(
                id = 1,
                favorite = false,
                name = "pikachu",
                base_experience = 50,
                height = 1,
                order = 1,
                is_default = true,
                location_area_encounters = "",
                weight = 1
            )
        )
        val remotePokemons = listOf(
            LocalPokemon(
                id = 2,
                favorite = false,
                name = "pikachu2",
                base_experience = 50,
                height = 1,
                order = 1,
                is_default = true,
                location_area_encounters = "",
                weight = 1
            )
        )
        val localDataSource = mock<LocalDataSource> {
            onBlocking { count() } doReturn 1 // se configura la funcion count() para que retorne 0 = db vacia
            onBlocking { pokemons } doReturn flowOf(localPokemons) // se configura la funcion count() para que retorne 0 = db vacia
        }
        val remoteDataSource = mock<RemoteDataSource> {
            onBlocking { getPokemons() } doReturn remotePokemons
        }
        val repository = PokemonRepository(localDataSource, remoteDataSource)

        // genera contexto de corrutina para testear
        val result = runBlocking {
            repository.getPokemons()
            repository.pokemons.first()
        }

        assertEquals(localPokemons, result)
    }

}