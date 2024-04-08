package com.example.desafio_android.model

import androidx.room.Entity

@Entity
data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)