package com.example.cocktailapp.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentTransaction;

import com.example.cocktailapp.NetworkService;
import com.example.cocktailapp.R;
import com.example.cocktailapp.entity.DrinkEntity;
import com.example.cocktailapp.entity.DrinkMapperUtil;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity2 extends AppCompatActivity {

    private ArrayList<DrinkEntity> drinkEntities;
    private SecondSearchFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        drinkEntities = new ArrayList<>();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) searchItem.getActionView();


        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {


                getSupportActionBar().setDisplayShowTitleEnabled(!hasFocus);
                if (!hasFocus)
                    searchView.onActionViewCollapsed();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                doMySearch(query);

                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.onActionViewCollapsed();

                findViewById(R.id.textview_first).setVisibility(View.INVISIBLE);
                fragment = new SecondSearchFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.search_nav_host_fragment, fragment).commit();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }

    public List<DrinkEntity> getDrinkEntities() {
        return drinkEntities;
    }

    public void doMySearch(String message) {

        NetworkService.getInstance()
                .getJSONApi()
                .getCocktails(message)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        List<DrinkEntity> entities = DrinkMapperUtil.mapDrinkJsonToDrinkEntity(response.body());
                        drinkEntities.clear();
                        if (entities == null) {
                            ThirdSearchFragment thirdSearchFragment = new ThirdSearchFragment();
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.search_nav_host_fragment, thirdSearchFragment).commit();
                        } else
                            drinkEntities.addAll(entities);

                        fragment.showData();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }

}
