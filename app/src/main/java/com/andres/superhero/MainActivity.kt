package com.andres.superhero

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.andres.superhero.database.SuperheroDatabase
import com.andres.superhero.database.dao.SuperheroDao
import com.andres.superhero.database.entities.ListadoHeroesEntity
import com.andres.superhero.database.entities.toDatabase
//import com.andres.superhero.database.entities.toDatabase
import com.andres.superhero.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit

    private lateinit var adapter: SuperheroAdapter

    private lateinit var room: SuperheroDatabase
    private lateinit var dao: SuperheroDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retrofit = getRetrofit()

        //Esto es para poder usar el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        room = Room.databaseBuilder(this, SuperheroDatabase::class.java, "superheroes2").build()
        dao = room.getHeroesDao()

        cargarSuperheroes()
        cargarSuperheroesDetalle()

        initComponents()

        initUI()

    }

    private fun initUI() {
        binding.searchbar.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Añadimos el orEmpty para que si está nulo no haga nada
                searchByName(query.orEmpty())
                return false
            }
            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = SuperheroAdapter { superheroId ->  navigateToDetail(superheroId) }
        binding.rvHeroes.setHasFixedSize(true)
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        binding.rvHeroes.adapter = adapter
    }


    private fun searchByName(query: String) {

        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: List<ListadoHeroesEntity> =
                dao.selectAllHeroes("%$query%")
            if (myResponse.size != null) {
                    runOnUiThread {
                        adapter.updateList(myResponse)
                        binding.progressBar.isVisible = false
                    }
                } else {
                    Log.i("Resultado", "No se encuentran resultados")
                    binding.progressBar.isVisible = false
                    binding.resultados.text = "No se han encontrado resultados"
                }

            }
        }




    private fun cargarSuperheroes() {

        binding.progressBar.isVisible = true
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<SuperHeroDataResponse> =
                retrofit.create(ApiService::class.java).getSuperheroes()

            if (myResponse.isSuccessful) {
                Log.i("Consulta", "Funciona :)")
                val response: SuperHeroDataResponse? = myResponse.body()
                if (response != null && response.response == "success") {
                    Log.i("Cuerpo de la consulta", response.toString())
                    //Primero eliminamos todos los datos
                    dao.deleteAllHeroes()

                    //Aqui insertamos response.superheroes
                    dao.insertAllHeroes(response.superheroes.map { it.toDatabase()})
                    Log.i("insertado", "success")
                    runOnUiThread {
                        binding.progressBar.isVisible = false
                    }
                } else {
                    Log.i("Resultado", "No se encuentran resultados")
                    binding.progressBar.isVisible = false
                    binding.resultados.text = "No se han encontrado resultados"
                }
            } else {
                Log.i("Consulta", "No funciona :(")
            }
        }
    }

    private fun cargarSuperheroesDetalle() {
        CoroutineScope(Dispatchers.IO).launch {
            val superheroDetail =
                getRetrofit().create(ApiService::class.java).getSuperheroDetail()
            if(superheroDetail.body() != null){
                var heroes = superheroDetail.body()!!.heroes
                dao.deleteAllHeroesDetail()
                dao.insertHeroDetail(heroes.map { it.toDatabase() })

                for(cadena in dao.debugHeroDetail()){
                    Log.i("DEBUG HERO DETAIL", cadena.fullName)
                }


                runOnUiThread {
                    for (hero in heroes){
                        Log.i("Hero detail", hero.biography.fullName)
                    }
                }
            }
        }
    }

    private fun navigateToDetail(id: String) {
        val intent = Intent(this, DetailSuperheroActivity::class.java)
        intent.putExtra(EXTRA_ID, id)
        Log.i("Cambiamos id", id)
        startActivity(intent)
    }


    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/api/25088195710767567/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initComponents() {
    }
}