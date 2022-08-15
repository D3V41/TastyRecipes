package com.deval.tastyrecipes.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.adapters.CategoryAdapter;
import com.deval.tastyrecipes.adapters.RecipesAdapter;
import com.deval.tastyrecipes.helpers.FetchData;
import com.deval.tastyrecipes.interfaces.VolleyCallback;
import com.deval.tastyrecipes.models.Category;
import com.deval.tastyrecipes.models.CategoryRecipe;
import com.deval.tastyrecipes.models.Recipe;
import com.deval.tastyrecipes.models.SearchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipesFragment} factory method to
 * create an instance of this fragment.
 */
public class RecipesFragment extends Fragment {

    //declaring widgets and variables
    ListView recipesList;
    TextView recipesTitle;
    View v;
    RequestQueue requestQueue;
    ArrayList<CategoryRecipe> recipeList;

    String categoryName;

    //setting up apis
    public static final String recipesURL = "https://www.themealdb.com/api/json/v1/1/filter.php?c=";

    public RecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_recipes, container, false);
        //calling init method to initialize all the variables and widgets
//        Intent i = getIntent();
//        Bundle b = i.getExtras();
//        if (b != null) {
//            recipeNameToSearch = b.getString("recipeTitle");
//        }
        categoryName = getArguments().getString("categoryName");
        Toast.makeText(getActivity(),categoryName+"",Toast.LENGTH_SHORT).show();
        init();
        return v;
    }

    //initializing all the variables and widgets
    //calling recipes list based on search view query
    private void init() {
        recipesList = v.findViewById(R.id.recipes_lv);
        recipesTitle = v.findViewById(R.id.recipes_title_tv);
        recipeList = new ArrayList<CategoryRecipe>();

        //Using volley to get data from http api and for the request queue created
        requestQueue = Volley.newRequestQueue(getActivity());

        getRecipes(recipesURL);

    }

    //getRecipes method getting recipes list under the selected category from Url using Volley and callback method
    public void getRecipes(String RecipeURL){

        //inside requestQueue calling FetchData.getRequest method to get the url response and extracting
        //data from that response and setting it up in Category class and then adding the data into the
        //category array list then creating new adapter and setting it into categories list
        requestQueue.add(FetchData.getRequest(RecipeURL+categoryName, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    recipesList.setAdapter(null);
                    recipesTitle.setText(categoryName+" Recipes");
                    JSONObject responseData = new JSONObject(result);

                    JSONArray categoriesArray = responseData.getJSONArray("meals");
                    Log.d("data",categoriesArray.length()+"");
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject recipe = categoriesArray.getJSONObject(i);
                        CategoryRecipe recipeDetails = new CategoryRecipe(recipe.getInt("idMeal"),recipe.getString("strMeal"),
                                recipe.getString("strMealThumb"));
                        recipeList.add(recipeDetails);

                    }
                    RecipesAdapter recipesListAdapter = new RecipesAdapter(getActivity(),recipeList);
                    recipesList.setAdapter(recipesListAdapter);
                } catch (JSONException e) {
                    //if there is no response then setting null adapter in categories list and chaning
                    //categories title text
                    recipesTitle.setText("No Results");
                    recipesList.setAdapter(null);
                    e.printStackTrace();
                }
            }

            //if there is any error which loading data from url then it will toast here
            @Override
            public void onError(String result) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        }));
    }
}