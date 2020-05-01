package com.example.cocktailapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.cocktailapp.entity.DrinkEntity;

@Database(entities = {DrinkEntity.class}, version = 1)
@TypeConverters(ConverterUtil.class)
public abstract class DrinkDatabase extends RoomDatabase {

    public abstract DrinkDao drinkDao();

    private static volatile DrinkDatabase drinkDatabaseInstance;

    public static DrinkDatabase getDatabase(final Context context) {
        if (drinkDatabaseInstance == null) {
            synchronized (DrinkDatabase.class) {
                if (drinkDatabaseInstance == null) {
                    drinkDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            DrinkDatabase.class, "drink_database")
                            .build();
                }
            }
        }
        return drinkDatabaseInstance;
    }

}
