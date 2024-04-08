package com.example.poke_desafio.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.desafio_android.data.PokemonRepository
import com.example.desafio_android.data.local.LocalPokemon
import com.example.poke_desafio.ui.theme.DesafioandroidTheme

@Composable
fun HomeScreen(pokemonRepository: PokemonRepository) {
    DesafioandroidTheme {
        val viewModel: HomeViewModel = viewModel { HomeViewModel(pokemonRepository) }
        // LIVEDATA
        //val state by viewModel.state.observeAsState(HomeViewModel.UiState())
        // STATEFLOW
        val state by viewModel.state.collectAsState()
        val selectedPkm by viewModel.selectedPkm.collectAsState()
        /*val pokemones =
            produceState<List<Pokemon>>(initialValue = emptyList()) { // lo mismo que LauchedEffect
                value = Retrofit.Builder()
                    .baseUrl("https://pokeapi.co/api/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(PokemonService::class.java)
                    .getPokemons()
                    .results
            }*/

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(5f)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.loading) {
                        CircularProgressIndicator()
                    }
                    if (state.pokemones.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                                .background(Color.Black),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.pokemones.size) {
                                val currentPkm = state.pokemones[it]
                                PokemonItem(
                                    pkm = currentPkm,
                                    onClick = {
                                        viewModel.onPokemonClick(currentPkm)
                                    }
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary),
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedPkm != null) {
                        AsyncImage(
                            model = selectedPkm?.sprites?.front_default,
                            contentDescription = selectedPkm?.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(pkm: LocalPokemon, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(Color.Red)
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        if (pkm.favorite) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = pkm.name,
                tint = Color.White
            )
        }
        Text(
            text = pkm.name,
            color = Color.White
        )
    }
}