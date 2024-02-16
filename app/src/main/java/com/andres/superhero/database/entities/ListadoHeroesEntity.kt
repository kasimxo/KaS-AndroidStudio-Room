package com.andres.superhero.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.andres.superhero.SuperHeroDetailResponse
import com.andres.superhero.SuperHeroItemResponse

@Entity(tableName = "listado_heroes")
data class ListadoHeroesEntity(

    //Tabla 1: id (clave primaria autogenerada), name (string) e image (string)

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image") val image: String
)
// Hay que especificarle los campos porque también existe el campo "id"
fun SuperHeroItemResponse.toDatabase() = ListadoHeroesEntity(name = name, image = image.url)
