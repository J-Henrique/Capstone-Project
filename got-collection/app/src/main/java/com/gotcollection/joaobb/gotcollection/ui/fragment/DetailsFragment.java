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
import android.widget.ListView;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.FragmentDetailsBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.gotcollection.joaobb.gotcollection.ui.util.ListViewDynamicHeightUtils;

import org.parceler.Parcels;

import java.util.Objects;

public class DetailsFragment extends Fragment {

    public static final String ARGS_CHARACTER = "args_character";

    private FragmentDetailsBinding mBinding;
    private CharacterEntity character;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false);

        assert getArguments() != null;
        character = Parcels.unwrap(getArguments().getParcelable(ARGS_CHARACTER));
        mBinding.setCharacter(character);

        setupDynamicListView(mBinding.lvTitles, character.getTitles());
        setupDynamicListView(mBinding.lvBooks, character.getBooks());

        return mBinding.getRoot();
    }

    private void setupDynamicListView(ListView listView, String[] objectsArray) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(Objects.requireNonNull(getActivity()), R.layout.layout_row, objectsArray);
        listView.setAdapter(arrayAdapter);

        ListViewDynamicHeightUtils.setListViewHeightBasedOnChildren(listView);
    }
}
