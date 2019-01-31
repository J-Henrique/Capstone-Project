package com.gotcollection.joaobb.gotcollection.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.FragmentDetailsBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.util.ListViewHeight;

import org.parceler.Parcels;

public class DetailsFragment extends Fragment {

    public static final String ARGS_CHARACTER = "args_character";

    FragmentDetailsBinding mBinding;
    CharacterEntity character;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

        character = Parcels.unwrap(getArguments().getParcelable(ARGS_CHARACTER));
        mBinding.setCharacter(character);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(getActivity(), R.layout.layout_row, character.getTitles());
        mBinding.lvTitles.setAdapter(arrayAdapter);

        ListViewHeight.setListViewHeightBasedOnChildren(mBinding.lvTitles);

        return mBinding.getRoot();
    }
}
