package com.gotcollection.joaobb.gotcollection.ui.activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.ActivityDetailsBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.async.DeleteCharacterTask;
import com.gotcollection.joaobb.gotcollection.ui.async.InsertCharacterTask;
import com.gotcollection.joaobb.gotcollection.ui.async.QueryCharacterFavoriteTask;
import com.gotcollection.joaobb.gotcollection.ui.fragment.DetailsFragment;
import com.gotcollection.joaobb.gotcollection.ui.widget.FavCharactersWidget;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_CHARACTER = "extra_selected_character";
    public static final String TRANSITION_PICTURE = "transition_picture";

    public ActivityDetailsBinding mBinding;

    private CharacterEntity mSelectedCharacter;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

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
                .replace(R.id.fr_details_container, detailsFragment)
                .commit();
    }

    public void fabClick(View view) {
        if (mBinding.getIsFavorite()) {
            new DeleteCharacterTask(this).execute(mSelectedCharacter);

            broadcastWidgetUpdate();
        } else {
            new InsertCharacterTask(this).execute(mSelectedCharacter);

            logFavoriteCharacter();

            broadcastWidgetUpdate();
        }
    }

    private void broadcastWidgetUpdate() {
        Intent updateWidget = new Intent(this, FavCharactersWidget.class);
        updateWidget.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int ids[] = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(
                new ComponentName(getApplication(), FavCharactersWidget.class));

        updateWidget.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        sendBroadcast(updateWidget);
    }

    private void logFavoriteCharacter() {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_ID, mSelectedCharacter.getId());
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, mSelectedCharacter.getName());
        mFirebaseAnalytics.logEvent("character_marked_favorite", params);
    }

    public void backClick(View view) {
        onBackPressed();
    }

    private void checkCharacterIsFavorite() {
        new QueryCharacterFavoriteTask(this).execute(mSelectedCharacter.getId());
    }
}
