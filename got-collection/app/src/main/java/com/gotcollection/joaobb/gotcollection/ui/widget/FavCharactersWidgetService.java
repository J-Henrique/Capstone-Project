package com.gotcollection.joaobb.gotcollection.ui.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class FavCharactersWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new FavCharactersWidgetRemoteViewFactory(this.getApplicationContext());
    }
}
