package com.example.cocktailapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.cocktailapp.DrinkRecyclerAdapter;
import com.example.cocktailapp.R;
import com.example.cocktailapp.database.DrinkRepository;
import com.example.cocktailapp.entity.DrinkEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FirstFragment extends Fragment {

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
        drinkEntities = new ArrayList<>();
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = view.findViewById(R.id.main_screen_recycler_view);
        layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);

        DrinkRepository drinkRepository = new DrinkRepository(view.getContext());
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


        drinkRepository.getDrinkList().observe(getViewLifecycleOwner(), new Observer<List<DrinkEntity>>() {
            @Override
            public void onChanged(List<DrinkEntity> entities) {
                Collections.reverse(entities);
                drinkEntities.clear();
                drinkEntities.addAll(entities);
                mAdapter.setNewData(entities);

                if (drinkEntities.size() == 0) {
                    NavHostFragment.findNavController(FirstFragment.this).navigate(R.id.action_FirstFragment_to_SecondFragment);
                }
            }
        });

    }

}

