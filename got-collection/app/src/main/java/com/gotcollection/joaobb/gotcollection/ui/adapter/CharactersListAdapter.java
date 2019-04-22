package com.gotcollection.joaobb.gotcollection.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.databinding.CharacterCardBinding;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CharactersListAdapter extends RecyclerView.Adapter<CharactersListAdapter.CharactersViewHolder> {

    private static final String TAG = CharactersListAdapter.class.getSimpleName();
    private List<CharacterEntity> mDataset;
    private Context mContext;

    private final CardClickListener onCardClick;
    private int lastPosition = -1;

    public interface CardClickListener {
        void onItemClick(Pair<CharacterEntity, View> characterEntityViewPair);
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

        Log.v(TAG, "Image URL: " + character.getImageLink());

        Picasso
            .get()
            .load(character.getImageLink())
            .noFade()
            .placeholder(R.drawable.ic_picture_icon)
            .resize(80, 80)
            .into(charactersViewHolder.mBinding.ivCirclePicture);

        charactersViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClick.onItemClick(new Pair<>(character, v));
            }
        });

        charactersViewHolder.bind(character);

        setAnimation(charactersViewHolder.itemView, i);
    }

    private void setAnimation(View viewToAnimate, int position) {
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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

        private final CharacterCardBinding mBinding;

        CharactersViewHolder(@NonNull CharacterCardBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void bind(CharacterEntity character) {
            mBinding.setCharacter(character);
            mBinding.executePendingBindings();
        }
    }
}
