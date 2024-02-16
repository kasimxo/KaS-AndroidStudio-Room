package com.andres.superhero

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse (
    @SerializedName("response") val response: String,
    @SerializedName("results") val superheroes: List<SuperHeroItemResponse>
)

data class SuperHeroItemResponse(
    @SerializedName("id") val superheroId: String,
    @SerializedName("name") val name: String,
    @SerializedName("image") val image: SuperHeroImageResponse

)



data class SuperHeroImageResponse(@SerializedName("url") val url:String)

data class SuperHeroDetailList(
    @SerializedName("results") val heroes: List<SuperHeroDetailResponse>
)

data class SuperHeroDetailResponse(
    @SerializedName("powerstats") val powerstats: PowerStatsResponse,
    @SerializedName("image") val image: SuperheroImageDetailResponse,
    @SerializedName("biography") val biography:Biography
)

data class PowerStatsResponse(
    @SerializedName("intelligence") val intelligence: String,
    @SerializedName("strength") val strength: String,
    @SerializedName("speed") val speed: String,
    @SerializedName("durability") val durability: String,
    @SerializedName("power") val power: String,
    @SerializedName("combat") val combat: String
)

data class SuperheroImageDetailResponse(@SerializedName("url") val url:String)

data class Biography(
    @SerializedName("full-name") val fullName:String,
    @SerializedName("publisher") val publisher:String
)


