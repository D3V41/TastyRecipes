package com.deval.tastyrecipes.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.activities.recipe;
import com.deval.tastyrecipes.models.CategoryRecipe;
import com.deval.tastyrecipes.models.SearchData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipesAdapter implements ListAdapter {
    //declaring variables
    ArrayList<CategoryRecipe> recipesList;
    Context context;

    //initializing variables
    public RecipesAdapter(Context context, ArrayList<CategoryRecipe> recipesList) {
        this.recipesList=recipesList;
        this.context=context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    //gets the size of the result
    @Override
    public int getCount() {
        return recipesList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //getting items from searchlist and setting it into the search result row
    //using picasso external library to show images which are in the form of http url
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            CategoryRecipe recipesData = recipesList.get(i);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.recipe_row_item,null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, recipesData.getRecipeTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, recipe.class);
                    intent.putExtra("recipeTitle", recipesData.getRecipeTitle());
                    context.startActivity(intent);
                }
            });
            TextView rTitle = view.findViewById(R.id.categoryRecipeTitle);
            ImageView rImage = view.findViewById(R.id.categoryRecipeImage);
            rTitle.setText(recipesData.getRecipeTitle());
            Picasso.get().load(recipesData.getRecipeImage()).into(rImage);
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return recipesList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
