package com.gotcollection.joaobb.gotcollection.ui.activity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.ActivityDetailsBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {

    public static final String EXTRA_SELECTED_CHARACTER = "extra_selected_character";

    ActivityDetailsBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_details);

        if (getIntent().hasExtra(EXTRA_SELECTED_CHARACTER)) {
            CharacterEntity selectedItem = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_SELECTED_CHARACTER));

            mBinding.setCharacter(selectedItem);

            String imagePath = getResources().getString(R.string.gotMiscUrl) + selectedItem.getImageLink();

            Picasso
                .get()
                .load(imagePath)
                .noFade()
                .into(mBinding.ivPicture);
        }
    }
}
