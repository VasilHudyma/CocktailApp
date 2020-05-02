package com.example.cocktailapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cocktailapp.entity.DrinkEntity;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DrinkDao {

    @Insert(onConflict = REPLACE)
    void insert(DrinkEntity drinkEntity);

    @Query("select * from drink")
    LiveData<List<DrinkEntity>> getAllDrinks();
}
