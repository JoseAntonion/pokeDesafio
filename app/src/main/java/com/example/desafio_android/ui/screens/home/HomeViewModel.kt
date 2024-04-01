package com.example.desafio_android.ui.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.desafio_android.data.PokemonRepository
import com.example.desafio_android.data.local.LocalPokemon
import com.example.desafio_android.model.ServerPokemon
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: PokemonRepository) : ViewModel() {

    // STATE
    /*var state by mutableStateOf(UiState())
        private set*/
    // LIVEDATA
    //private val _state = MutableLiveData(UiState())
    //val state: LiveData<UiState> = _state
    // STATEFLOW
    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    private val _selectedPkm: MutableStateFlow<ServerPokemon?> = MutableStateFlow(null)
    val selectedPkm: StateFlow<ServerPokemon?> = _selectedPkm

    data class UiState(
        val loading: Boolean = false,
        val pokemones: List<LocalPokemon> = emptyList()
    )

    init {
        viewModelScope.launch {
            UiState(loading = true)
            repository.getPokemons()
            repository.pokemons.collect {
                _state.value = UiState(pokemones = it)
            }
        }
    }

    fun onPokemonClick(pkm: LocalPokemon) {
        try {
            viewModelScope.launch {
                _selectedPkm.value = repository.getPokemon(pkm.name)
                //repository.updatePokemon(pkm.copy(favorite = !pkm.favorite))
            }
        } catch (e: Exception) {
            Log.e("TAG", e.message.toString())
        }
    }

}