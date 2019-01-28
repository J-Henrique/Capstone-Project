package com.gotcollection.joaobb.gotcollection.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.ActivityMainBinding;
import com.gotcollection.joaobb.gotcollection.ui.adapter.CharactersFragmentPagerAdapter;
import com.gotcollection.joaobb.gotcollection.viewmodel.MainViewModel;

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
