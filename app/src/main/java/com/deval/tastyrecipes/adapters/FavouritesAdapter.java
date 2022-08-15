package com.deval.tastyrecipes.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.activities.recipe;
import com.deval.tastyrecipes.models.SearchData;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchData> favouriteList;
    CardView favouriteCard;
    Context _context;
    public FavouritesAdapter(List<SearchData> listOfRecipes, Context context) {
        super();
        _context = context;
        favouriteList = listOfRecipes;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favouritecard,parent,false);
        favouriteCard = view.findViewById(R.id.favouriteCard);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            SearchData recipeObj = favouriteList.get(position);
            ((ViewHolder) holder).fRecipeTitle.setText(String.valueOf(recipeObj.getRecipeTitle()));
            ((ViewHolder) holder).fRecipeCategory.setText(recipeObj.getRecipeCategory());
            favouriteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(_context, recipe.class);
                intent.putExtra("recipeTitle", recipeObj.getRecipeTitle());
                _context.startActivity(intent);
            }
            });
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
