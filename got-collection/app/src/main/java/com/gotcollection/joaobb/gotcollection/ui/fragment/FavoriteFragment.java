package com.gotcollection.joaobb.gotcollection.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.FragmentFavoriteBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.adapter.CharactersListAdapter;
import com.gotcollection.joaobb.gotcollection.ui.util.EqualSpacingItemDecorationUtils;
import com.gotcollection.joaobb.gotcollection.viewmodel.MainViewModel;

import java.util.List;

public class FavoriteFragment extends Fragment implements CharactersListAdapter.CardClickListener {

    CharactersListAdapter mFavoriteListAdapter;

    FragmentFavoriteBinding mBinding;
    MainViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);

        mFavoriteListAdapter = new CharactersListAdapter(this);
        mBinding.favoritesList.rvCharactersList.setAdapter(mFavoriteListAdapter);
        mBinding.favoritesList.rvCharactersList.addItemDecoration(new EqualSpacingItemDecorationUtils(25));

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel.getLoadingFlagObservable().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading) {
                    mBinding.favoritesList.pbLoading.setVisibility(View.VISIBLE);
                } else {
                    mBinding.favoritesList.pbLoading.setVisibility(View.GONE);
                }
            }
        });

        mViewModel.getFavoritesObservable().observe(getActivity(), new Observer<List<CharacterEntity>>() {
            @Override
            public void onChanged(@Nullable List<CharacterEntity> characterEntities) {
                mFavoriteListAdapter.setDataset(characterEntities);
                mViewModel.hideLoading();
            }
        });

        mViewModel.loadCharacters();
    }

    @Override
    public void onItemClick(Pair<CharacterEntity, View> characterEntityViewPair) {
        mViewModel.setSelectedCharacter(characterEntityViewPair);
    }
}
