package com.gotcollection.joaobb.gotcollection.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.CharacterCardBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharactersListAdapter extends RecyclerView.Adapter<CharactersListAdapter.CharactersViewHolder> {

    private List<CharacterEntity> mDataset;
    private Context mContext;

    private CardClickListener onCardClick;

    public interface CardClickListener {
        void onItemClick(CharacterEntity selectedItem);
    }

    public CharactersListAdapter(CardClickListener onItemClick) {
        onCardClick = onItemClick;
    }

    @NonNull
    @Override
    public CharactersViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        CharacterCardBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(viewGroup.getContext()),
                R.layout.character_card,
                viewGroup,
                false);

        mContext = viewGroup.getContext();

        return new CharactersViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersViewHolder charactersViewHolder, int i) {
        final CharacterEntity character = mDataset.get(i);

        String imagePath = mContext.getResources().getString(R.string.gotMiscUrl) + character.getImageLink();

        Picasso
            .get()
            .load(imagePath)
            .noFade()
            .placeholder(R.drawable.ic_people)
            .resize(80, 80)
            .into(charactersViewHolder.mBinding.ivCirclePicture);

        charactersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClick.onItemClick(character);
            }
        });

        charactersViewHolder.bind(character);
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public void setDataset(List<CharacterEntity> charactersList) {
        mDataset = charactersList;
        notifyDataSetChanged();
    }

    public class CharactersViewHolder extends RecyclerView.ViewHolder {

        private CharacterCardBinding mBinding;

        public CharactersViewHolder(@NonNull CharacterCardBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void bind(CharacterEntity character) {
            mBinding.setCharacter(character);
            mBinding.executePendingBindings();
        }
    }
}
