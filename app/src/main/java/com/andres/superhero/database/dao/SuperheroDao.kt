package com.andres.superhero.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.andres.superhero.database.entities.DetalleHeroEntity
import com.andres.superhero.database.entities.ListadoHeroesEntity

@Dao
interface SuperheroDao {

    @Query("SELECT * FROM listado_heroes")
    suspend fun getAllHeroes():List<ListadoHeroesEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllHeroes(heroe:List<ListadoHeroesEntity>)

    @Query("DELETE FROM listado_heroes")
    suspend fun deleteAllHeroes()

    @Query("SELECT * FROM detalle_heroe WHERE id=:id")
    suspend fun selectHeroDetail(id: Int): DetalleHeroEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHeroDetail(heroeDetail: List<DetalleHeroEntity>)

    @Query("DELETE FROM detalle_heroe")
    suspend fun deleteAllHeroesDetail()

}
