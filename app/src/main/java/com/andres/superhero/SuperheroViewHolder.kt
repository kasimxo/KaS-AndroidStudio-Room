package com.andres.superhero

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.andres.superhero.databinding.HerorecyclerviewBinding
import com.squareup.picasso.Picasso

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = HerorecyclerviewBinding.bind(view)

    fun bind(superheroItemResponse: SuperHeroItemResponse, navigateToDetailActivity: (String) -> Unit) {
        binding.tvSuperheroName.text = superheroItemResponse.name
        Picasso.get().load(superheroItemResponse.image.url).into(binding.ivSuperhero)
        binding.root.setOnClickListener {
            navigateToDetailActivity(superheroItemResponse.superheroId)
        }
    }

}
