package com.example.desafio_android.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.desafio_android.model.Sprites
import kotlinx.coroutines.flow.Flow

@Database(entities = [LocalPokemon::class], version = 1)
abstract class PokemonDataBase : RoomDatabase() {
    abstract fun pokemonsDao(): PokemonDao
}

@Dao
interface PokemonDao {

    @Query("SELECT * FROM LocalPokemon")
    fun getPokemons(): Flow<List<LocalPokemon>>

    @Update
    suspend fun updatePokemon(pokemon: LocalPokemon)

    @Insert
    suspend fun insertAll(pokemons: List<LocalPokemon>)

    @Query("SELECT COUNT(*) FROM LocalPokemon")
    suspend fun count(): Int
}

@Entity
data class LocalPokemon(
    @PrimaryKey(autoGenerate = true) val id: Int,
    //val abilities: List<Ability>,
    val base_experience: Int,
    //val forms: List<Form>,
    //val game_indices: List<GameIndice>,
    val height: Int,
    //val held_items: List<HeldItem>,
    val is_default: Boolean,
    val location_area_encounters: String,
    //val moves: List<Move>,
    val name: String,
    val order: Int,
    //val past_types: List<PastType>,
    //val species: Species,
    //val sprites: Sprites,
    //val stats: List<Stat>,
    //val types: List<TypeXX>,
    val weight: Int,
    val favorite: Boolean = false
)