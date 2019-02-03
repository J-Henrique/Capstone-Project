package com.gotcollection.joaobb.gotcollection.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.ActivityDetailsBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.fragment.DetailsFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_CHARACTER = "extra_selected_character";
    public static final String TRANSITION_PICTURE = "transition_picture";

    ActivityDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        if (getIntent().hasExtra(EXTRA_SELECTED_CHARACTER)) {
            Parcelable wrappedCharacter = getIntent().getParcelableExtra(EXTRA_SELECTED_CHARACTER);
            CharacterEntity selectedItem = Parcels.unwrap(wrappedCharacter);

            mBinding.setCharacter(selectedItem);

            setupPicture(selectedItem);

            setupDetailsFragment(wrappedCharacter);
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
}
