package com.gotcollection.joaobb.gotcollection.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.Repository;
import com.gotcollection.joaobb.gotcollection.databinding.ActivityDetailsBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.fragment.DetailsFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_CHARACTER = "extra_selected_character";
    public static final String TRANSITION_PICTURE = "transition_picture";

    ActivityDetailsBinding mBinding;
    CharacterEntity mSelectedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        if (getIntent().hasExtra(EXTRA_SELECTED_CHARACTER)) {
            Parcelable wrappedCharacter = getIntent().getParcelableExtra(EXTRA_SELECTED_CHARACTER);
            mSelectedCharacter = Parcels.unwrap(wrappedCharacter);

            mBinding.setCharacter(mSelectedCharacter);

            setupPicture(mSelectedCharacter);

            setupDetailsFragment(wrappedCharacter);

            checkCharacterIsFavorite();
        }
    }

    private void setupPicture(CharacterEntity selectedItem) {
        String imagePath = getResources().getString(R.string.gotMiscUrl) + selectedItem.getImageLink();

        Picasso
            .get()
            .load(imagePath)
            .placeholder(R.drawable.ic_picture_expanded)
            .noFade()
            .into(mBinding.ivPicture);
    }

    private void setupDetailsFragment(Parcelable wrappedCharacter) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(DetailsFragment.ARGS_CHARACTER, wrappedCharacter);

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fr_details_container, detailsFragment)
                .commit();
    }

    public void fabClick(View view) {
        if (mBinding.getIsFavorite()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    Repository.getInstance(DetailsActivity.this).deleteCharacter(mSelectedCharacter);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(DetailsActivity.this, getResources().getString(R.string.favorite_message_remove), Toast.LENGTH_LONG).show();

                    mBinding.setIsFavorite(false);
                }
            }.execute();
        } else {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    Repository.getInstance(DetailsActivity.this).insertCharacter(mSelectedCharacter);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    Toast.makeText(DetailsActivity.this, getResources().getString(R.string.favorite_message_add), Toast.LENGTH_LONG).show();

                    mBinding.setIsFavorite(true);
                }
            }.execute();
        }
    }

    private void checkCharacterIsFavorite() {
        new AsyncTask<String, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(String... strings) {
                return Repository.getInstance(DetailsActivity.this).isFavorite(strings[0]);
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                mBinding.setIsFavorite(aBoolean);
            }
        }.execute(mSelectedCharacter.getId());
    }
}
