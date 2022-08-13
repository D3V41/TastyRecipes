package com.deval.tastyrecipes.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.activities.recipe;
import com.deval.tastyrecipes.adapters.FavouritesAdapter;
import com.deval.tastyrecipes.helpers.FetchData;
import com.deval.tastyrecipes.interfaces.VolleyCallback;
import com.deval.tastyrecipes.models.FavouriteRecipe;
import com.deval.tastyrecipes.models.Recipe;
import com.deval.tastyrecipes.models.SearchData;
import com.deval.tastyrecipes.models.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragment extends Fragment {

    DatabaseReference reference;
    RequestQueue requestQueue;
    View v;
    FavouritesAdapter favouritesAdapter;
    RecyclerView rcView;
    List<SearchData> favouriteList;
    ArrayList<FavouriteRecipe> favouriteRecipe;
    public static final String recipeByName = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

    public FavouriteFragment() {
        // Required empty public constructor

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favourite,container,false);
        rcView = v.findViewById(R.id.rcView);
        favouriteList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        reference = FirebaseDatabase.getInstance().getReference("Favourites");
        Query likedRecipe = reference.orderByChild("favouriteRecipeName");
        likedRecipe.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 favouriteRecipe = new ArrayList<FavouriteRecipe>();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()) {
                     favouriteRecipe.add(dataSnapshot.getValue(FavouriteRecipe.class));
                }

                Log.i("Favourite Recipe", String.valueOf(favouriteRecipe.size()));
                //search for all recipes using recipe names from array List
                searchForRecipes(favouriteRecipe);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               // Snackbar.make(profileFrame, "Something Went Wrong!", Snackbar.LENGTH_LONG).show();
            }
        });

        bindAdapter();
        return v;
    }

    private void searchForRecipes(ArrayList<FavouriteRecipe> favouriteRecipes) {

        for(FavouriteRecipe fr: favouriteRecipes) {
            requestQueue.add(FetchData.getRequest(recipeByName + fr.getFavouriteRecipeName(), new VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject responseData = new JSONObject(result);
                        JSONArray mealsArray = responseData.getJSONArray("meals");
                        SearchData recipeCreate = createSearchDataObj(mealsArray.getJSONObject(0));

                        favouriteList.add(recipeCreate);
                        Log.i("onfosnf",favouriteList+"");
//                    Log.i("Data",recipeCreate.getRecipeTags()+"");
                        favouritesAdapter = new FavouritesAdapter(favouriteList,getActivity());
                        rcView.setAdapter(favouritesAdapter);
                        favouritesAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {

                        rcView.setAdapter(null);
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String result) {

                }
            }));



        }




    }
    private void bindAdapter() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        rcView.setLayoutManager(layoutManager);
        favouritesAdapter = new FavouritesAdapter(favouriteList,getActivity());
        rcView.setAdapter(favouritesAdapter);
        favouritesAdapter.notifyDataSetChanged();
        Log.i("onfosnf",favouriteList+"");


    }

    private SearchData createSearchDataObj(JSONObject favouriteRecipe) {
        String title = "",category = "",image = "";
        try {
            title = favouriteRecipe.getString("strMeal");
            category = favouriteRecipe.getString("strCategory");
            image = favouriteRecipe.getString("strMealThumb");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new SearchData(title,category,image);
    }


}