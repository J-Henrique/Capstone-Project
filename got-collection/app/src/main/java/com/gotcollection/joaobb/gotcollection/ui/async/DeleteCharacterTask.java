package com.gotcollection.joaobb.gotcollection.ui.async;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.Repository;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.activity.DetailsActivity;

import java.lang.ref.WeakReference;

public class DeleteCharacterTask extends AsyncTask<CharacterEntity, Void, Void> {

    private WeakReference<Context> weakContext;

    public DeleteCharacterTask(Context context) {
        weakContext = new WeakReference<>(context);
    }

    @Override
    protected Void doInBackground(CharacterEntity... voids) {
        Repository.getInstance(weakContext.get()).deleteCharacter(voids[0]);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);

        Toast.makeText(
                weakContext.get(),
                weakContext.get().getResources().getString(R.string.favorite_message_remove),
                Toast.LENGTH_LONG).show();

        ((DetailsActivity) weakContext.get()).mBinding.setIsFavorite(false);
    }
}
