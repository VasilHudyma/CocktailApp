package com.example.cocktailapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cocktailapp.DrinkRecyclerAdapter;
import com.example.cocktailapp.NetworkService;
import com.example.cocktailapp.R;
import com.example.cocktailapp.entity.DrinkEntity;
import com.example.cocktailapp.entity.DrinkMapperUtil;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DrinkRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
                if(!hasFocus)
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


        return super.onCreateOptionsMenu(menu);
    }


    private void doMySearch(String message) {
        recyclerView = findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);


        NetworkService.getInstance()
                .getJSONApi()
                .getCocktails(message)
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        List<DrinkEntity> drinkEntities = DrinkMapperUtil.mapDrinkJsonToDrinkEntity(response.body());

                        if (drinkEntities==null){
                            return;
                        }

                        //   mAdapter = new MyRecyclerViewAdapter(SearchActivity.this, drinkEntities);
                        mAdapter = new DrinkRecyclerAdapter(R.layout.drink_holder, drinkEntities, SearchActivity.this);

                        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                                Intent intent = new Intent(SearchActivity.this, DrinkScreenActivity.class);
                                intent.putExtra("drinkEntity", drinkEntities.get(i));
                                startActivity(intent);
                            }
                        });
                        recyclerView.setAdapter(mAdapter);
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });
    }
}
