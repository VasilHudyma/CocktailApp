package com.example.cocktailapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.cocktailapp.R;
import com.example.cocktailapp.database.DrinkRepository;
import com.example.cocktailapp.entity.DrinkEntity;

public class DrinkScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_screen);


        Intent intent = getIntent();
        DrinkEntity drinkEntity = (DrinkEntity) intent.getSerializableExtra("drinkEntity");

        DrinkRepository drinkRepository = new DrinkRepository(this);

        drinkEntity.setId(0);
        drinkRepository.insert(drinkEntity);

        TextView name = findViewById(R.id.textDrinkName);
        TextView alcoholic = findViewById(R.id.textDrinkAlcoholic);
        TextView glass = findViewById(R.id.textDrinkGlass);
        TextView ingredients = findViewById(R.id.ingredients);
        TextView measures = findViewById(R.id.measures);
        TextView instructions = findViewById(R.id.instructions);

        name.setText(drinkEntity.getName());
        alcoholic.setText(drinkEntity.getAlcoholic());
        glass.setText(drinkEntity.getGlass());
        getSupportActionBar().setTitle(drinkEntity.getName());

        ingredients.setMaxLines(drinkEntity.getIngredients().size());
        measures.setMaxLines(drinkEntity.getIngredients().size());

        String text = "";

        for (int i = 0; i < drinkEntity.getIngredients().size(); i++) {
            text = text.concat((i + 1) + ". " + drinkEntity.getIngredients().get(i)+"\n");
        }

        ingredients.setText(text);

        String str = "";
        for (int i =0; i<drinkEntity.getIngredientsMeasure().size();i++){
            str = str.concat(drinkEntity.getIngredientsMeasure().get(i)+"\n");
        }

        measures.setText(str);

        instructions.setText(drinkEntity.getInstruction());

        ImageView imageView = findViewById(R.id.imageViewDrinkProfile);
        Glide.with(this).load(drinkEntity.getDrinkThumb()).into(imageView);

    }


}
