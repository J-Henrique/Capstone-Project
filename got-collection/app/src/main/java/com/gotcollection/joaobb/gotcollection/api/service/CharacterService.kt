package com.gotcollection.joaobb.gotcollection.api.service

import com.gotcollection.joaobb.gotcollection.api.model.CharacterResultModel
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterService {

    @GET("characters")
    fun getCharacters(): Call<Array<CharacterEntity>>

    @GET("characters/{name}")
    fun getCharacterByName(@Path("name") name: String): Call<CharacterResultModel>
}
