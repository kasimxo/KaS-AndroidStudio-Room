package com.andres.superhero.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.andres.superhero.database.dao.SuperheroDao
import com.andres.superhero.database.entities.DetalleHeroEntity
import com.andres.superhero.database.entities.ListadoHeroesEntity

//import com.andres.basededatos.data.database.dao.QuoteDao
//import com.andres.basededatos.data.database.entities.QuoteEntity

@Database(entities = [DetalleHeroEntity::class, ListadoHeroesEntity::class], version = 1)
abstract class SuperheroDatabase: RoomDatabase() {

    abstract fun getHeroesDao(): SuperheroDao
}