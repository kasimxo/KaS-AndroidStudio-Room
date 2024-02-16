package com.andres.superhero.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andres.superhero.SuperHeroDetailResponse


@Entity(tableName = "detalle_heroe")
data class DetalleHeroEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "intelligence") val intelligence: String,
    @ColumnInfo(name = "strength") val strength: String,
    @ColumnInfo(name = "speed") val speed: String,
    @ColumnInfo(name = "durability") val durability: String,
    @ColumnInfo(name = "power") val power: String,
    @ColumnInfo(name = "combat") val combat: String,
    @ColumnInfo(name = "fullName") val fullName: String,
    @ColumnInfo(name = "publisher") val publisher: String,
)

fun SuperHeroDetailResponse.toDatabase() = DetalleHeroEntity(
    intelligence = powerstats.intelligence,
    strength = powerstats.strength,
    speed = powerstats.speed,
    durability = powerstats.durability,
    power = powerstats.power,
    combat = powerstats.combat,
    fullName = biography.fullName,
    publisher = biography.publisher)