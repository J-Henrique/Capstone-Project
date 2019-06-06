package com.gotcollection.joaobb.gotcollection.api.model

import com.google.gson.annotations.SerializedName
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity

class CharacterResultModel(@field:SerializedName("data")
                           val characterResult: CharacterEntity)
