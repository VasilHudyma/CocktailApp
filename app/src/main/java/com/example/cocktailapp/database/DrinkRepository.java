package com.example.cocktailapp.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.cocktailapp.entity.DrinkEntity;

import java.util.List;

public class DrinkRepository {
    private DrinkDao drinkDao;
    private DrinkDatabase drinkDatabase;
    private LiveData<List<DrinkEntity>> drinkList;

    public DrinkRepository(Context context) {
        drinkDatabase = DrinkDatabase.getDatabase(context);
        drinkDao = drinkDatabase.drinkDao();
        drinkList = drinkDao.getAllDrinks();
    }

    public void insert(DrinkEntity entity){
        new InsertAsyncTask(drinkDao).execute(entity);
    }

    public LiveData<List<DrinkEntity>> getDrinkList() {
        return drinkList;
    }

    private class InsertAsyncTask extends AsyncTask<DrinkEntity,Void,Void> {

        DrinkDao dao;

        public InsertAsyncTask(DrinkDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(DrinkEntity... drinkEntities) {
            dao.insert(drinkEntities[0]);
            return null;
        }
    }
}
