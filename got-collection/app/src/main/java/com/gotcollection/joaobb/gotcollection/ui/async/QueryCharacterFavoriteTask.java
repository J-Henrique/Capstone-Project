package com.gotcollection.joaobb.gotcollection.ui.async;

import android.content.Context;
import android.os.AsyncTask;

import com.gotcollection.joaobb.gotcollection.Repository;
import com.gotcollection.joaobb.gotcollection.ui.activity.DetailsActivity;

import java.lang.ref.WeakReference;

public class QueryCharacterFavoriteTask extends AsyncTask<String, Void, Boolean> {

    private WeakReference<Context> weakContext;

    public QueryCharacterFavoriteTask(Context context) {
        weakContext = new WeakReference<>(context);
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        return Repository.getInstance(weakContext.get()).isFavorite(strings[0]);
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);

        ((DetailsActivity) weakContext.get()).mBinding.setIsFavorite(aBoolean);
    }
}
