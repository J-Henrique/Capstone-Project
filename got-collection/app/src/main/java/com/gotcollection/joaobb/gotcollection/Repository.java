package com.gotcollection.joaobb.gotcollection;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.gotcollection.joaobb.gotcollection.api.model.CharacterResultModel;
import com.gotcollection.joaobb.gotcollection.api.service.CharacterService;
import com.gotcollection.joaobb.gotcollection.db.AppDatabase;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository {

    private static final String TAG = Repository.class.getSimpleName();

    private final AppDatabase appDatabase;
    private static Repository sRepositoryInstance;

    private final CharacterService characterService;

    private List<CharacterEntity> cachedCharacters;

    private final MutableLiveData<List<CharacterEntity>> charactersObservable = new MutableLiveData<>();

    private Repository(final Context context) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(context.getResources().getString(R.string.gotApiUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Api service creation
        characterService = retrofit.create(CharacterService.class);

        // Database instantiation
        appDatabase = AppDatabase.getInstance(context);
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

    public LiveData<List<CharacterEntity>> loadFavoritesLiveData() {
        return appDatabase.characterDao().loadFavoritesLiveData();
    }

    public List<CharacterEntity> loadFavorites() {
        return appDatabase.characterDao().loadFavorites();
    }

    public void insertCharacter(CharacterEntity character) {
        appDatabase.characterDao().insertCharacter(character);
    }

    public void deleteCharacter(CharacterEntity character) {
        appDatabase.characterDao().deleteCharacter(character);
    }

    public boolean isFavorite(String characterId) {
        return appDatabase.characterDao().isFavorite(characterId);
    }

    public LiveData<List<CharacterEntity>> getCharactersObservable() {
        return charactersObservable;
    }

    public void loadCharacters() {
        if (cachedCharacters == null) {
            characterService.getCharacters().enqueue(new Callback<CharacterEntity[]>() {
                @Override
                public void onResponse(@NonNull Call<CharacterEntity[]> call, @NonNull Response<CharacterEntity[]> response) {
                    Log.d(TAG, "onResponse() returned: " + Arrays.asList(response.body()).size());

                    /*
                        ATTENTION:
                        Due large API mass, the characters being returned when no filter is applied is limited up to 50
                    */

                    // cache characters to prevent multiple API calls
                    cachedCharacters = Arrays.asList(response.body()).subList(0, 50);

                    charactersObservable.setValue(cachedCharacters);
                }

                @Override
                public void onFailure(@NonNull Call<CharacterEntity[]> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: t", t.getCause());

                    charactersObservable.setValue(null);
                }
            });
        } else {
            charactersObservable.setValue(cachedCharacters);
        }
    }

    public void loadCharacterByName(@NonNull String characterName) {
        characterService.getCharacterByName(characterName).enqueue(new Callback<CharacterResultModel>() {
            @Override
            public void onResponse(@NonNull Call<CharacterResultModel> call, @NonNull Response<CharacterResultModel> response) {
                Log.d(TAG, "onResponse: " + response.body());

                if (response.body() != null) {
                    charactersObservable.setValue(Arrays.asList(response.body().getCharacterResult()));
                } else {
                    charactersObservable.setValue(null);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CharacterResultModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: t", t);

                charactersObservable.setValue(null);
            }
        });
    }
}
