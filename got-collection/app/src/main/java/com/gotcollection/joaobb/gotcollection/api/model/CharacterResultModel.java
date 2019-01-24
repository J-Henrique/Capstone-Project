package com.gotcollection.joaobb.gotcollection.api.model;

import com.google.gson.annotations.SerializedName;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

public class CharacterResultModel {

    @SerializedName("data")
    private CharacterEntity characterResult;

    public CharacterResultModel(CharacterEntity characterResult) {
        this.characterResult = characterResult;
    }

    public CharacterEntity getCharacterResult() {
        return characterResult;
    }
}
