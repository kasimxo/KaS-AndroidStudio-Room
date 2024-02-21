package com.andres.superhero

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andres.superhero.database.entities.ListadoHeroesEntity

class SuperheroAdapter( var superheroList: List<ListadoHeroesEntity> = emptyList(),
                        private val navigateToDetailActivity: (String) -> Unit) : RecyclerView.Adapter<SuperheroViewHolder>() {

    fun updateList(list: List<ListadoHeroesEntity>) {
        superheroList = list
        notifyDataSetChanged()

        for (s in list){
            Log.d("Lista", s.image)
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        return SuperheroViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.herorecyclerview, parent, false)
        )
    }
    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
        holder.bind(superheroList[position],navigateToDetailActivity)
    }
    override fun getItemCount() = superheroList.size
}
