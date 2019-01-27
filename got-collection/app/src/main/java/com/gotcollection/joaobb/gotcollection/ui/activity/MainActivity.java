package com.gotcollection.joaobb.gotcollection.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.ActivityMainBinding;
import com.gotcollection.joaobb.gotcollection.ui.adapter.CharactersFragmentPagerAdapter;
import com.gotcollection.joaobb.gotcollection.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding mBinding;
    MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new MainViewModel(getApplication());

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.setViewModel(mViewModel);

        mBinding.toolbar.inflateMenu(R.menu.main);

        CharactersFragmentPagerAdapter pagerAdapter = new CharactersFragmentPagerAdapter(this, getSupportFragmentManager());
        mBinding.viewPager.setAdapter(pagerAdapter);

        setupTabLayout();
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
