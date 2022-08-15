package com.deval.tastyrecipes.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.activities.recipe;
import com.deval.tastyrecipes.fragments.RecipesFragment;
import com.deval.tastyrecipes.models.Category;
import com.deval.tastyrecipes.models.SearchData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryAdapter implements ListAdapter {
    //declaring variables
    ArrayList<Category> categoryList;
    Context context;

    //initializing variables
    public CategoryAdapter(Context context, ArrayList<Category> categoryList) {
        this.categoryList=categoryList;
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
        return categoryList.size();
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
            Category categoryData = categoryList.get(i);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.category_row_item,null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, categoryData.getCategoryTitle(), Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(context, RecipesFragment.class);
//                    intent.putExtra("categoryId", categoryData.getCategoryId());
//                    context.startActivity(intent);
                    FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                    RecipesFragment rf = new RecipesFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("categoryName",String.valueOf(categoryData.getCategoryTitle()));
                    rf.setArguments(bundle);
                    transaction.replace(R.id.frame,rf).commit();
                }
            });
            TextView cTitle = view.findViewById(R.id.categoryTitle);
            TextView cCategory = view.findViewById(R.id.categoryDesc);
            ImageView cImage = view.findViewById(R.id.categoryImage);
            cTitle.setText(categoryData.getCategoryTitle());
            cCategory.setText(categoryData.getCategoryDescription());
            Picasso.get().load(categoryData.getCategoryImage()).into(cImage);
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return categoryList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}