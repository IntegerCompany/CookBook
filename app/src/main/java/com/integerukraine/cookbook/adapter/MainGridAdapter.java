package com.integerukraine.cookbook.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.integerukraine.cookbook.R;
import com.integerukraine.cookbook.parse.ParseKey;
import com.integerukraine.cookbook.utils.Convertations;
import com.parse.ParseObject;

import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by andrew on 17.11.15.
 */
public class MainGridAdapter extends RecyclerView.Adapter<MainGridAdapter.TrackViewHolder> {

    ArrayList<ParseObject> recipes = new ArrayList<>();

    int columns;

    public MainGridAdapter(int columns){
        this.columns = columns;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_grid_recipe, parent, false);
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        try {
            ParseObject recipe = recipes.get(position);
            holder.tvDescription.setText(recipe.getString(ParseKey.Recipe.DESCRIPTION));
            holder.tvDishName.setBackgroundColor(Color.parseColor(recipe.getParseObject(ParseKey.Recipe.GRID_IMAGE).getString(ParseKey.Image.COLOR)));
            holder.tvDishName.setText(recipe.getString(ParseKey.Recipe.DISH_NAME));
            if (position < columns) {
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.cardRecipe.getLayoutParams();
                layoutParams.topMargin = Convertations.dpToPx(8, holder.itemView.getContext());
                holder.cardRecipe.setLayoutParams(layoutParams);
            }
            Glide.with(holder.itemView.getContext())
                    .load(recipe.getParseObject(ParseKey.Recipe.GRID_IMAGE).getString(ParseKey.Image.URL))
                    .centerCrop()
                    .placeholder(R.color.colorAccent)
                    .override(recipe.getParseObject(ParseKey.Recipe.GRID_IMAGE).getJSONArray(ParseKey.Image.RESOLUTION).getInt(0),
                            recipe.getParseObject(ParseKey.Recipe.GRID_IMAGE).getJSONArray(ParseKey.Image.RESOLUTION).getInt(1))
                    .crossFade()
                    .into(holder.imageRecipe);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void add(ArrayList<ParseObject> items){
        int recipesSize = recipes.size();
        recipes.addAll(items);
        notifyItemRangeInserted(recipesSize, items.size());
    }


    class TrackViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription;
        TextView tvDishName;
        ImageView imageRecipe;
        CardView cardRecipe;

        public TrackViewHolder(View itemView) {
            super(itemView);

            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            tvDishName = (TextView) itemView.findViewById(R.id.tv_dish_name);
            imageRecipe = (ImageView) itemView.findViewById(R.id.image_recipe);
            cardRecipe = (CardView) itemView.findViewById(R.id.card_recipe);
        }
    }
}
