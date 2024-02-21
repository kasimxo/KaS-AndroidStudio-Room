package com.andres.superhero

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.room.Room
import com.andres.superhero.MainActivity.Companion.EXTRA_ID
import com.andres.superhero.database.SuperheroDatabase
import com.andres.superhero.database.dao.SuperheroDao
import com.andres.superhero.database.entities.DetalleHeroEntity
import com.andres.superhero.database.entities.ListadoHeroesEntity
import com.andres.superhero.databinding.ActivityDetailSuperheroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperheroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailSuperheroBinding

    private lateinit var room: SuperheroDatabase
    private lateinit var dao: SuperheroDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        room = Room.databaseBuilder(this, SuperheroDatabase::class.java, "superheroes2").build()
        dao = room.getHeroesDao()

        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        Log.i("Segunda", id)
        getSuperheroInformation(id.toInt())
    }

    private fun getSuperheroInformation(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val superheroDetail = dao.selectHeroDetail(id)
            val heroe = dao.selectHero(id)
            Log.i("Heroe", heroe.name)
            //Log.i("Heroe detail", superheroDetail.fullName)
            if(superheroDetail != null){

                    createUI(superheroDetail!!, heroe)

            }
        }
    }

    private fun createUI(superhero: DetalleHeroEntity, heroe: ListadoHeroesEntity) {
        Log.i("heroe name", heroe.name)
        Log.i("superhero", superhero.durability)

        runOnUiThread {
            Picasso.get().load(heroe.image).into(binding.ivSuperhero)
            binding.tvSuperheroName.text = heroe.name
            binding.tvSuperheroRealName.text = superhero.fullName
            binding.tvPublisher.text = superhero.publisher
            prepareStats(superhero)
        }


    }
    private fun prepareStats(superhero: DetalleHeroEntity) {
        updateHeight(binding.viewIntelligence, superhero.intelligence)
        updateHeight(binding.viewStrength, superhero.strength)
        updateHeight(binding.viewSpeed, superhero.speed)
        updateHeight(binding.viewDurability, superhero.durability)
        updateHeight(binding.viewPower, superhero.power)
        updateHeight(binding.viewCombat, superhero.combat)
    }

    private fun updateHeight(view: View, stat:String){
        val params = view.layoutParams
        if (stat != "null") {
            params.height = pxToDp(stat.toFloat())
        } else {
            params.height = pxToDp(0.toFloat())
        }
        view.layoutParams = params
    }

    private fun pxToDp(px:Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics).roundToInt()
    }


}

private fun getRetrofit(): Retrofit {
    return Retrofit
        .Builder()
        .baseUrl("https://superheroapi.com/api/25088195710767567/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}





