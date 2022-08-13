package com.deval.tastyrecipes.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.models.SearchData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchData> favouriteList;
    public FavouritesAdapter(List<SearchData> listOfRecipes, Context context) {
        super();
        favouriteList = listOfRecipes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favouritecard,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            SearchData recipeObj = favouriteList.get(position);
            ((ViewHolder) holder).fRecipeTitle.setText(String.valueOf(recipeObj.getRecipeTitle()));
            ((ViewHolder) holder).fRecipeCategory.setText(recipeObj.getRecipeCategory());
            Picasso.get().load(recipeObj.getRecipeImage()).into(((ViewHolder) holder).fRecipeImage);

    }

    @Override
    public int getItemCount() {
        return favouriteList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public TextView fRecipeTitle,fRecipeCategory;
    public ImageView fRecipeImage;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        fRecipeTitle = (TextView) itemView.findViewById(R.id.favouriteRecipeTitle);
        fRecipeCategory = (TextView) itemView.findViewById(R.id.favouriteRecipeCategory);
        fRecipeImage = (ImageView) itemView.findViewById(R.id.favouriteRecipeImage);
    }
}
