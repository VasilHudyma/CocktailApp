package com.example.cocktailapp.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

public class DrinkMapperUtil {

    public static List<DrinkEntity> mapDrinkJsonToDrinkEntity(JsonObject object) {

        if (object.get("drinks").isJsonNull()) {
            return null;
        }
        JsonArray array = object.getAsJsonArray("drinks");
        List<DrinkEntity> entityList = new ArrayList<>();

        for (int i = 0; i < array.size(); i++) {
            entityList.add(DrinkEntity.builder()
                    .id(array.get(i).getAsJsonObject().get("idDrink").getAsInt())
                    .name(array.get(i).getAsJsonObject().get("strDrink").getAsString())
                    .alcoholic(array.get(i).getAsJsonObject().get("strAlcoholic").getAsString())
                    .glass(array.get(i).getAsJsonObject().get("strGlass").getAsString())
                    .instruction(array.get(i).getAsJsonObject().get("strInstructions").getAsString())
                    .drinkThumb(array.get(i).getAsJsonObject().get("strDrinkThumb").getAsString())
                    .ingredients(new ArrayList<String>())
                    .ingredientsMeasure(new ArrayList<String>())
                    .build());

            List<String> ingredients = entityList.get(i).getIngredients();

            for (int j = 1; j < 16; j++) {
                ingredients.add(array.get(i).getAsJsonObject().get("strIngredient" + j).getAsString());
                if (array.get(i).getAsJsonObject().get("strIngredient" + (j + 1)).isJsonNull() || (array.get(i).getAsJsonObject().get("strIngredient" + (j + 1)).getAsString().isEmpty())) {
                    break;
                }
            }

            entityList.get(i).setIngredients(ingredients);

            List<String> listMeasure = entityList.get(i).getIngredientsMeasure();

            for (int j = 1; j < ingredients.size() + 1; j++) {
                if (array.get(i).getAsJsonObject().get("strMeasure" + j).isJsonNull()) {
                    break;
                }
                listMeasure.add(array.get(i).getAsJsonObject().get("strMeasure" + j).getAsString());
                if (array.get(i).getAsJsonObject().get("strMeasure" + (j + 1)).isJsonNull() || (array.get(i).getAsJsonObject().get("strMeasure" + (j + 1)).getAsString().isEmpty())) {
                    break;
                }
            }

            entityList.get(i).setIngredientsMeasure(listMeasure);
        }

        return entityList;
    }
}
