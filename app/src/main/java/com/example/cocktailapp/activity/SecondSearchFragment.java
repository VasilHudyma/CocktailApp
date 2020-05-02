package com.example.cocktailapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cocktailapp.DrinkRecyclerAdapter;
import com.example.cocktailapp.R;
import com.example.cocktailapp.entity.DrinkEntity;

import java.util.List;

public class SecondSearchFragment extends Fragment {


    private RecyclerView recyclerView;
    private DrinkRecyclerAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<DrinkEntity> drinkEntities;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_search_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.search_recyclerView);
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        SearchActivity searchActivity = (SearchActivity) getActivity();

        drinkEntities = searchActivity.getDrinkEntities();

        mAdapter = new DrinkRecyclerAdapter(R.layout.drink_holder, drinkEntities, view.getContext());

        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(view.getContext(), DrinkScreenActivity.class);
                intent.putExtra("drinkEntity", drinkEntities.get(i));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapter);

    }


    public void showData() {
        mAdapter.setNewData(drinkEntities);
    }


    @Override
    public void onResume() {
        mAdapter.setNewData(drinkEntities);
        super.onResume();
    }
}
