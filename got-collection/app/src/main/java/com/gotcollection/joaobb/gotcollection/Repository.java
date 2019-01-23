package com.gotcollection.joaobb.gotcollection;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.gotcollection.joaobb.gotcollection.db.AppDatabase;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

import java.util.List;

public class Repository {

    private final AppDatabase appDatabase;
    private static Repository sRepositoryInstance;

    private Repository(final Context context) {
        this.appDatabase = AppDatabase.getInstance(context);
    }

    public static Repository getInstance(final Context context) {
        if (sRepositoryInstance == null) {
            synchronized (Repository.class) {
                if (sRepositoryInstance == null) {
                    sRepositoryInstance = new Repository(context);
                }
            }
        }
        return sRepositoryInstance;
    }

    public LiveData<List<CharacterEntity>> loadFavoriteCharacters() {
        return appDatabase.characterDao().loadCharacters();
    }

    public void insertCharacter(CharacterEntity character) {
        appDatabase.characterDao().insertCharacter(character);
    }
}
