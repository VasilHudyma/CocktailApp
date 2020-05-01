package com.example.cocktailapp.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.cocktailapp.database.ConverterUtil;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity(tableName = "drink", indices = {@Index(value = {"name"}, unique = true)})
@NoArgsConstructor
public class DrinkEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;


    @NonNull
    private String name;

    private String alcoholic;

    private String glass;

    private String instruction;

    private String drinkThumb;

    @ColumnInfo(name = "ingredients")
    @TypeConverters(ConverterUtil.class)
    private List<String> ingredients;

    @ColumnInfo(name = "measures")
    @TypeConverters(ConverterUtil.class)
    private List<String> ingredientsMeasure;
}
