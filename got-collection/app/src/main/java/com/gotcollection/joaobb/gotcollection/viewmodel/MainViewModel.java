package com.gotcollection.joaobb.gotcollection.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.view.View;

import com.gotcollection.joaobb.gotcollection.Repository;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private LiveData<List<CharacterEntity>> charactersLiveData;
    private LiveData<List<CharacterEntity>> favoriteCharactersLiveData;
    private MutableLiveData<Boolean> loadingFlagLiveData;
    private MutableLiveData<Pair<CharacterEntity, View>> selectedCharacterViewPairLiveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CharacterEntity>> getCharactersObservable() {
        if (charactersLiveData == null) {
            charactersLiveData = Repository.getInstance(getApplication()).getCharactersObservable();
        }

        return charactersLiveData;
    }

    public LiveData<List<CharacterEntity>> getFavoritesObservable() {
        if (favoriteCharactersLiveData == null) {
            favoriteCharactersLiveData = Repository.getInstance(getApplication()).loadFavoriteCharacters();
        }

        return favoriteCharactersLiveData;
    }

    public LiveData<Boolean> getLoadingFlagObservable() {
        if (loadingFlagLiveData == null) {
            loadingFlagLiveData = new MutableLiveData<>();
        }

        return loadingFlagLiveData;
    }

    public LiveData<Pair<CharacterEntity, View>> getSelectedCharacterObservable() {
        if (selectedCharacterViewPairLiveData == null) {
            selectedCharacterViewPairLiveData = new MutableLiveData<>();
        }

        return selectedCharacterViewPairLiveData;
    }

    public void loadCharacters() {
        loadingFlagLiveData.setValue(true);
        Repository.getInstance(getApplication()).loadCharacters();
    }

    public void loadCharacterByName(@NonNull String characterName) {
        loadingFlagLiveData.setValue(true);
        Repository.getInstance(getApplication()).loadCharacterByName(characterName);
    }

    public void hideLoading() {
        loadingFlagLiveData.setValue(false);
    }

    public void setSelectedCharacter(Pair<CharacterEntity, View> characterEntityViewPair) {
        selectedCharacterViewPairLiveData.setValue(characterEntityViewPair);
    }
}
