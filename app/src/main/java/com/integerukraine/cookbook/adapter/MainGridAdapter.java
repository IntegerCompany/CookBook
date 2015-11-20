package com.integerukraine.cookbook.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by andrew on 17.11.15.
 */
public class MainGridAdapter extends RecyclerView.Adapter<MainGridAdapter.TrackViewHolder> {

    ArrayList<ParseObject> recipes = new ArrayList<>();

    int columns;
    Typeface arialBlack;
    Typeface helveticaNeue;

    public MainGridAdapter(int columns){
        this.columns = columns;
    }

    @Override
    public TrackViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_grid_recipe, parent, false);
        arialBlack = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/arial_black.ttf");
        helveticaNeue = Typeface.createFromAsset(parent.getContext().getAssets(), "fonts/helvetica_neue.ttf");
        return new TrackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackViewHolder holder, int position) {
        try {
            ParseObject recipe = recipes.get(position);
            initSizes(recipe, holder, position);
            initFonts(recipe, holder, position);
            initTextViews(recipe, holder, position);
            initImages(recipe, holder, position);
            initListeners(recipe, holder, position);



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Used to work with sizes of view
     */
    private void initSizes(ParseObject recipe, TrackViewHolder holder, int position) {
        if (position < columns) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.cardRecipe.getLayoutParams();
            layoutParams.topMargin = Convertations.dpToPx(8, holder.itemView.getContext());
            holder.cardRecipe.setLayoutParams(layoutParams);
        }
        holder.cardRecipe.setRadius(12);
    }
    /**
     * Used to work with fonts of view
     */
    private void initFonts(ParseObject recipe, TrackViewHolder holder, int position) {

        holder.tvDishCalories.setTypeface(arialBlack);
        holder.tvDishTime.setTypeface(arialBlack);
        holder.tvDishType.setTypeface(arialBlack);
        holder.tvUsername.setTypeface(arialBlack);

        holder.tvDescription.setTypeface(helveticaNeue);
        holder.tvDishCalories.setTypeface(helveticaNeue);
        holder.tvDishName.setTypeface(helveticaNeue);
    }

    private void initTextViews(ParseObject recipe, TrackViewHolder holder, int position) {
//        Setting texts
        holder.tvDescription.setText(recipe.getString(ParseKey.Recipe.DESCRIPTION));
        holder.tvDishName.setText(recipe.getString(ParseKey.Recipe.DISH_NAME));

//        Setting colors & images
        holder.tvDishName.setBackgroundColor(Color.parseColor(recipe.getParseObject(ParseKey.Recipe.GRID_IMAGE).getString(ParseKey.Image.COLOR)));

    }

    private void initImages(ParseObject recipe, TrackViewHolder holder, int position) throws JSONException {
        Glide.with(holder.itemView.getContext())
                .load(recipe.getParseObject(ParseKey.Recipe.GRID_IMAGE).getString(ParseKey.Image.URL))
                .centerCrop()
                .placeholder(R.color.colorAccent)
                .override(recipe.getParseObject(ParseKey.Recipe.GRID_IMAGE).getJSONArray(ParseKey.Image.RESOLUTION).getInt(0),
                        recipe.getParseObject(ParseKey.Recipe.GRID_IMAGE).getJSONArray(ParseKey.Image.RESOLUTION).getInt(1))
                .crossFade()
                .into(holder.imageRecipe);
    }

    private void initListeners(ParseObject recipe, final TrackViewHolder holder, int position){
        holder.cardRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), Scr.class));
            }
        });
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
        TextView tvDishName, tvDescription, tvDishType, tvDishCalories, tvDishTime, tvUsername, tvUserStatus;
        ImageView imageRecipe;
        CircleImageView imageUserAvatar;
        CardView cardRecipe;


        public TrackViewHolder(View itemView) {
            super(itemView);

            tvDishName = (TextView) itemView.findViewById(R.id.tv_dish_name);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_dish_description);
            tvDishType = (TextView) itemView.findViewById(R.id.tv_dish_type);
            tvDishCalories = (TextView) itemView.findViewById(R.id.tv_dish_calories);
            tvDishTime = (TextView) itemView.findViewById(R.id.tv_dish_time);
            tvUsername = (TextView) itemView.findViewById(R.id.tv_username);
            tvUserStatus = (TextView) itemView.findViewById(R.id.tv_user_status);
            imageRecipe = (ImageView) itemView.findViewById(R.id.image_recipe);
            imageUserAvatar = (CircleImageView) itemView.findViewById(R.id.image_user_avatar);
            cardRecipe = (CardView) itemView.findViewById(R.id.card_recipe);
        }
    }
}
