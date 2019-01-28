package com.gotcollection.joaobb.gotcollection.ui.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.FragmentCharactersBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.adapter.CharactersListAdapter;
import com.gotcollection.joaobb.gotcollection.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class CharacterFragment extends Fragment {

    CharactersListAdapter mCharactersListAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    FragmentCharactersBinding mBinding;
    MainViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false);

        mCharactersListAdapter = new CharactersListAdapter();
        mBinding.rvCharactersList.setAdapter(mCharactersListAdapter);

        mLayoutManager = new LinearLayoutManager(getContext());
        mBinding.rvCharactersList.setLayoutManager(mLayoutManager);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
        mViewModel.loadCharacters().observe(this, new Observer<List<CharacterEntity>>() {
            @Override
            public void onChanged(@Nullable List<CharacterEntity> characterEntities) {
                mCharactersListAdapter.setDataset(characterEntities);
            }
        });
    }
}
