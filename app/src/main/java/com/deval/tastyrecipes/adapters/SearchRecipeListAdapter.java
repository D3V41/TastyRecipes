package com.deval.tastyrecipes.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.models.SearchData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//search recipe adapter using which showing all the results on search fragment
public class SearchRecipeListAdapter implements ListAdapter {
    //declaring variables
    ArrayList<SearchData> searchList;
    Context context;

    //initializing variables
    public SearchRecipeListAdapter(Context context, ArrayList<SearchData> searchData) {
        this.searchList=searchData;
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
        return searchList.size();
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
            SearchData searchData = searchList.get(i);
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            view = layoutInflater.inflate(R.layout.search_row_item,null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, searchData.getRecipeTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            TextView rTitle = view.findViewById(R.id.recipeTitle);
            TextView rCategory = view.findViewById(R.id.recipeCategory);
            ImageView rImage = view.findViewById(R.id.recipeImage);
            rTitle.setText(searchData.getRecipeTitle());
            rCategory.setText(searchData.getRecipeCategory());
            Picasso.get().load(searchData.getRecipeImage()).into(rImage);
        }
        return view;
    }

    @Override
    public int getItemViewType(int i) {
        return i;
    }

    @Override
    public int getViewTypeCount() {
        return searchList.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
