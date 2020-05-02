package com.example.cocktailapp.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;

import com.example.cocktailapp.R;
import com.example.cocktailapp.database.DrinkRepository;
import com.example.cocktailapp.entity.DrinkEntity;

import java.util.ArrayList;
import java.util.List;

public class MainScreenSecondFragment extends Fragment {
    private List<DrinkEntity> drinkEntities;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment

        drinkEntities = new ArrayList<>();
        return inflater.inflate(R.layout.main_screen_second_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        DrinkRepository drinkRepository = new DrinkRepository(view.getContext());

        drinkRepository.getDrinkList().observe(getViewLifecycleOwner(), new Observer<List<DrinkEntity>>() {
            @Override
            public void onChanged(List<DrinkEntity> entities) {
                drinkEntities.addAll(entities);
                if (drinkEntities.size() > 0) {
                    NavHostFragment.findNavController(MainScreenSecondFragment.this).navigate(R.id.action_SecondFragment_to_FirstFragment);
                }
            }
        });

    }
}
