package com.example.desafio_android.model

import androidx.room.Entity
import com.example.desafio_android.data.local.LocalPokemon
import kotlinx.coroutines.flow.Flow

@Entity
data class ServerPokemon(
    val abilities: List<Ability>?,
    val base_experience: Int?,
    val forms: List<Form>?,
    val game_indices: List<GameIndice>?,
    val height: Int?,
    val held_items: List<HeldItem>?,
    val id: Int,
    val is_default: Boolean?,
    val location_area_encounters: String?,
    val moves: List<Move>?,
    val name: String?,
    val order: Int?,
    val past_types: List<PastType>?,
    val species: Species?,
    val sprites: Sprites?,
    val stats: List<Stat>?,
    val types: List<TypeXX>?,
    val weight: Int?,
    val favorite: Boolean = false
)

fun ServerPokemon.toLocalPokemon() = LocalPokemon(
    //abilities = abilities ?: emptyList(),
    base_experience = base_experience ?: 0,
    //forms = forms ?: emptyList(),
    //game_indices = game_indices ?: emptyList(),
    height = height ?: 0,
    //held_items = held_items ?: emptyList(),
    id = 0,
    is_default = is_default ?: false,
    location_area_encounters = location_area_encounters ?: "",
    //moves = moves ?: emptyList(),
    name = name ?: "",
    order = order ?: 0,
    //past_types = past_types ?: emptyList(),
    //species = species ?: Species("", ""),
    //sprites = sprites ?: Sprites(),
    //stats = stats ?: emptyList(),
    //types = types ?: emptyList(),
    weight = weight ?: 0
)