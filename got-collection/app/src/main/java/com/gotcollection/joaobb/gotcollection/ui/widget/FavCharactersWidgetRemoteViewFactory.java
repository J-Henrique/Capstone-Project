package com.gotcollection.joaobb.gotcollection.ui.widget;

import android.content.Context;
import android.widget.RemoteViews;

import com.gotcollection.joaobb.gotcollection.R;
import com.gotcollection.joaobb.gotcollection.Repository;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

import java.util.List;

class FavCharactersWidgetRemoteViewFactory implements FavCharactersWidgetService.RemoteViewsFactory{

    private final Context mContext;
    private List<CharacterEntity> mFavoritesList;

    public FavCharactersWidgetRemoteViewFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        mFavoritesList = Repository.getInstance(mContext).loadFavorites();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mFavoritesList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        remoteViews.setTextViewText(R.id.widget_tv_character_name, mFavoritesList.get(position).getName());

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
