package com.gotcollection.joaobb.gotcollection.ui.fragment;

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

import java.util.ArrayList;

public class CharacterFragment extends Fragment {

    CharactersListAdapter mCharactersListAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    FragmentCharactersBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters, container, false);

        mCharactersListAdapter = new CharactersListAdapter();
        mBinding.rvCharactersList.setAdapter(mCharactersListAdapter);

        mLayoutManager = new LinearLayoutManager(getContext());
        mBinding.rvCharactersList.setLayoutManager(mLayoutManager);

        mCharactersListAdapter.setDataset(new ArrayList<CharacterEntity>());

        return mBinding.getRoot();
    }
}
