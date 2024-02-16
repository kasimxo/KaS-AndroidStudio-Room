package com.andres.superhero

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("search/sp")
    suspend fun getSuperheroes(): Response<SuperHeroDataResponse>
    //@GET("{id}")
    //suspend fun getSuperheroDetail(@Path("id") superheroId:String): Response<SuperHeroDetailResponse>

    @GET("search/sp")
    suspend fun getSuperheroDetail():Response<SuperHeroDetailList>
}