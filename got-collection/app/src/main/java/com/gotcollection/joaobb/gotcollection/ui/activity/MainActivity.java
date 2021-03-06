package com.gotcollection.joaobb.gotcollection.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.ActivityMainBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.adapter.CharactersFragmentPagerAdapter;
import com.gotcollection.joaobb.gotcollection.viewmodel.MainViewModel;

import org.parceler.Parcels;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;
    private MainViewModel mViewModel;

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setViewModel(mViewModel);

        CharactersFragmentPagerAdapter pagerAdapter = new CharactersFragmentPagerAdapter(this, getSupportFragmentManager());
        mBinding.viewPager.setAdapter(pagerAdapter);

        setupSearchViewListeners();

        setupTabLayout();

        final Activity activity = this;

        mViewModel.getSelectedCharacterObservable().observe(this, new Observer<Pair<CharacterEntity, View>>() {
            @Override
            public void onChanged(@Nullable Pair<CharacterEntity, View> characterEntityViewPair) {
                    Intent startDetailsActivity = new Intent(getApplicationContext(), DetailsActivity.class);
                    startDetailsActivity.putExtra(DetailsActivity.EXTRA_SELECTED_CHARACTER, Parcels.wrap(Objects.requireNonNull(characterEntityViewPair).first));

                logSelectedCharacter(characterEntityViewPair);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        // View of the card clicked
                        final View view = characterEntityViewPair.second;

                        Bundle options = ActivityOptionsCompat
                                .makeSceneTransitionAnimation(activity, Objects.requireNonNull(view).findViewById(R.id.iv_circle_picture), DetailsActivity.TRANSITION_PICTURE)
                                .toBundle();

                        startActivity(startDetailsActivity, options);
                    } else {
                        startActivity(startDetailsActivity);
                    }
                }
        });
    }

    private void logSelectedCharacter(@NonNull Pair<CharacterEntity, View> characterEntityViewPair) {
        Bundle params = new Bundle();
        params.putString(FirebaseAnalytics.Param.ITEM_ID, Objects.requireNonNull(characterEntityViewPair.first).getId());
        params.putString(FirebaseAnalytics.Param.ITEM_NAME, characterEntityViewPair.first.getName());
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params);
    }

    private void setupSearchViewListeners() {

        // On searching a character
        mBinding.svFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mViewModel.loadCharacterByName(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        // On closing search view filter
        mBinding.svFilter.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mViewModel.loadCharacters();
                return false;
            }
        });
    }

    private void setupTabLayout() {
        TabLayout tabLayout = mBinding.tabLayout;
        tabLayout.setupWithViewPager(mBinding.viewPager);

        // Characters tab
        Objects.requireNonNull(tabLayout.getTabAt(0)).setIcon(R.drawable.ic_people);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setText(R.string.tab_item_characters);

        // Favorite tab
        Objects.requireNonNull(tabLayout.getTabAt(1)).setIcon(R.drawable.ic_filled_fav);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setText(R.string.tab_item_favorites);
    }
}
