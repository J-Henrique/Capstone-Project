package com.gotcollection.joaobb.gotcollection.ui.activity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.ActivityMainBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.adapter.CharactersFragmentPagerAdapter;
import com.gotcollection.joaobb.gotcollection.viewmodel.MainViewModel;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setViewModel(mViewModel);

        CharactersFragmentPagerAdapter pagerAdapter = new CharactersFragmentPagerAdapter(this, getSupportFragmentManager());
        mBinding.viewPager.setAdapter(pagerAdapter);

        setupSearchViewListeners();

        setupTabLayout();

        final Activity activity = this;

        mViewModel.getSelectedCharacterObservable().observe(this, new Observer<CharacterEntity>() {
            @Override
            public void onChanged(@Nullable CharacterEntity characterEntity) {
                Intent startDetailsActivity = new Intent(getApplicationContext(), DetailsActivity.class);
                startDetailsActivity.putExtra(DetailsActivity.EXTRA_SELECTED_CHARACTER, Parcels.wrap(characterEntity));

                final View view = findViewById(R.id.iv_circle_picture);

                Bundle options = ActivityOptionsCompat
                        .makeSceneTransitionAnimation(activity, view, "picture")
                        .toBundle();

                startActivity(startDetailsActivity, options);
            }
        });
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
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_people);
        tabLayout.getTabAt(0).setText(R.string.tab_item_characters);

        // Favorite tab
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_favorite);
        tabLayout.getTabAt(1).setText(R.string.tab_item_favorites);
    }
}
