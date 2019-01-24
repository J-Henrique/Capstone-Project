package com.gotcollection.joaobb.gotcollection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.gotcollection.joaobb.gotcollection.Repository;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<CharacterEntity>> charactersLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CharacterEntity>> loadCharacters() {
        if (charactersLiveData == null) {
            charactersLiveData = Repository.getInstance(getApplication()).loadCharacters();
        }

        return charactersLiveData;
    }
}
