package com.gotcollection.joaobb.gotcollection;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

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

    private CharacterService characterService;

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

    public LiveData<List<CharacterEntity>> loadFavoriteCharacters() {
        return appDatabase.characterDao().loadCharacters();
    }

    public void insertCharacter(CharacterEntity character) {
        appDatabase.characterDao().insertCharacter(character);
    }

    public LiveData<List<CharacterEntity>> loadCharacters() {
        final MutableLiveData<List<CharacterEntity>> data = new MutableLiveData<>();

        characterService.getCharacters().enqueue(new Callback<CharacterEntity[]>() {
            @Override
            public void onResponse(Call<CharacterEntity[]> call, Response<CharacterEntity[]> response) {
                Log.d(TAG, "onResponse() returned: " + Arrays.asList(response.body()).size());

                data.setValue(Arrays.asList(response.body()));
            }

            @Override
            public void onFailure(Call<CharacterEntity[]> call, Throwable t) {
                Log.e(TAG, "onFailure: t", t);

                data.setValue(null);
            }
        });

        return data;
    }
}
