package com.gotcollection.joaobb.gotcollection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.gotcollection.joaobb.gotcollection.Repository;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<CharacterEntity>> charactersLiveData;
    private LiveData<List<CharacterEntity>> favoriteCharactersLiveData;
    private MutableLiveData<Boolean> loadingFlagLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CharacterEntity>> getCharactersObservable() {
        if (charactersLiveData == null) {
            charactersLiveData = Repository.getInstance(getApplication()).getCharactersObservable();
        }

        return charactersLiveData;
    }

    public LiveData<List<CharacterEntity>> loadFavoriteCharacters() {
        if (favoriteCharactersLiveData == null) {
            favoriteCharactersLiveData = Repository.getInstance(getApplication()).getCharactersObservable();
        }

        return favoriteCharactersLiveData;
    }

    public LiveData<Boolean> getLoadingFlagObservable() {
        if (loadingFlagLiveData == null) {
            loadingFlagLiveData = new MutableLiveData<>();
        }

        return loadingFlagLiveData;
    }

    public void loadCharacters() {
        loadingFlagLiveData.setValue(true);
        Repository.getInstance(getApplication()).loadCharacters();
    }

    public void loadCharacterByName(@NonNull String characterName) {
        loadingFlagLiveData.setValue(true);
        Repository.getInstance(getApplication()).loadCharacterByName(characterName);
    }

    public void addToFavorites(CharacterEntity character) {
        Repository.getInstance(getApplication()).insertCharacter(character);
    }

    public void hideLoading() {
        loadingFlagLiveData.setValue(false);
    }
}
