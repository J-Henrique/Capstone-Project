package com.gotcollection.joaobb.gotcollection.db.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity

@Dao
interface CharacterDao {

    @Query("SELECT * FROM characters")
    fun loadFavoritesLiveData(): LiveData<List<CharacterEntity>>

    @Query("SELECT * FROM characters")
    fun loadFavorites(): List<CharacterEntity>

    @Query("SELECT 1 FROM characters WHERE _id = :characterId")
    fun isFavorite(characterId: String): Boolean

    @Delete
    fun deleteCharacter(character: CharacterEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterEntity)
}
