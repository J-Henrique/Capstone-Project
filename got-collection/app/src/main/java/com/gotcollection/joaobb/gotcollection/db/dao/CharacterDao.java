package com.gotcollection.joaobb.gotcollection.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

import java.util.List;

@Dao
public interface CharacterDao {

    @Query("SELECT * FROM characters")
    LiveData<List<CharacterEntity>> loadCharacters();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCharacter(CharacterEntity character);
}