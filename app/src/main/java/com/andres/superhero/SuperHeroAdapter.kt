package com.andres.superhero

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class SuperheroAdapter( var superheroList: List<SuperHeroItemResponse> = emptyList(),
                        private val navigateToDetailActivity: (String) -> Unit) : RecyclerView.Adapter<SuperheroViewHolder>() {

    fun updateList(list: List<SuperHeroItemResponse>) {
        superheroList = list
        notifyDataSetChanged()

        for (s in list){
            Log.d("Lista", s.image.url)
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
