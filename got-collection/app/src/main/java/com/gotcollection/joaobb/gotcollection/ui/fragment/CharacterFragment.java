package com.gotcollection.joaobb.gotcollection.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gotcollection.joaobb.gotcollection.ui.util.EqualSpacingItemDecoration;
import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.FragmentCharactersBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.adapter.CharactersListAdapter;
import com.gotcollection.joaobb.gotcollection.viewmodel.MainViewModel;

import java.util.List;

public class CharacterFragment extends Fragment implements CharactersListAdapter.CardClickListener {

    CharactersListAdapter mCharactersListAdapter;

    FragmentCharactersBinding mBinding;
    MainViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false);

        mCharactersListAdapter = new CharactersListAdapter(this);
        mBinding.rvCharactersList.setAdapter(mCharactersListAdapter);
        mBinding.rvCharactersList.addItemDecoration(new EqualSpacingItemDecoration(16));

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
                    mBinding.pbLoading.setVisibility(View.VISIBLE);
                } else {
                    mBinding.pbLoading.setVisibility(View.GONE);
                }
            }
        });

        mViewModel.getCharactersObservable().observe(getActivity(), new Observer<List<CharacterEntity>>() {
            @Override
            public void onChanged(@Nullable List<CharacterEntity> characterEntities) {
                mCharactersListAdapter.setDataset(characterEntities);
                mViewModel.hideLoading();
            }
        });

        mViewModel.loadCharacters();
    }

    @Override
    public void onItemClick(CharacterEntity selectedItem) {
        mViewModel.setSelectedCharacter(selectedItem);
    }
}