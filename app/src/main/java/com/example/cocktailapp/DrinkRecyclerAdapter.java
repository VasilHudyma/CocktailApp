package com.example.cocktailapp;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.cocktailapp.entity.DrinkEntity;

import java.util.List;

public class DrinkRecyclerAdapter extends BaseQuickAdapter<DrinkEntity, BaseViewHolder> {

    Context context;

    public DrinkRecyclerAdapter(int layoutResId, @Nullable List<DrinkEntity> data, Context context) {
        super(layoutResId, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, DrinkEntity entity) {
        helper.setText(R.id.textDrinkName, entity.getName());

        Glide.with(context).load(entity.getDrinkThumb()).into((ImageView) helper.getView(R.id.imageViewDrinkItem));

    }


}
