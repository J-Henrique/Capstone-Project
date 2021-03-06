package com.gotcollection.joaobb.gotcollection.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.gotcollection.joaobb.gotcollection.db.converter.Converters;
import com.gotcollection.joaobb.gotcollection.db.dao.CharacterDao;
import com.gotcollection.joaobb.gotcollection.db.entity.CharacterEntity;

@Database(entities = {CharacterEntity.class}, version = 2, exportSchema = false )
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "gotcollection";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        return sInstance;
    }

    public abstract CharacterDao characterDao();
}
