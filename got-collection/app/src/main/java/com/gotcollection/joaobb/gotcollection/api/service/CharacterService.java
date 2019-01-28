package com.gotcollection.joaobb.gotcollection.api.service;

import com.gotcollection.joaobb.gotcollection.api.model.CharacterResultModel;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CharacterService {

    @GET("characters")
    Call<CharacterEntity[]> getCharacters();

    @GET("characters/{name}")
    Call<CharacterResultModel> getCharacterByName(@Path("name") String name);
}
