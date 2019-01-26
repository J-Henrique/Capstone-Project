package com.gotcollection.joaobb.gotcollection.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gotcollection.joaobb.gotcollection.ui.fragment.FavoriteFragment;
import com.gotcollection.joaobb.gotcollection.ui.fragment.CharacterFragment;

public class CharactersFragmentPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public CharactersFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int i) {
        if (i == 0) {
            return new CharacterFragment();
        } else
            return new FavoriteFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }
}
