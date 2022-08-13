package com.deval.tastyrecipes.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.adapters.SearchRecipeListAdapter;
import com.deval.tastyrecipes.helpers.FetchData;
import com.deval.tastyrecipes.interfaces.VolleyCallback;
import com.deval.tastyrecipes.models.Recipe;
import com.deval.tastyrecipes.models.SearchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    //declaring widgets and variables
    ListView recipeList;
    SearchView searchView;
        ProgressBar searchPB;
    TextView searchTitle;
    View v;
    ArrayList<SearchData> searchDataArrayList;
    RequestQueue requestQueue;

    //setting up apis
    public static final String randomRecipeURL = "https://www.themealdb.com/api/json/v1/1/random.php";
    public static final String firstLetterRecipeURL = "https://www.themealdb.com/api/json/v1/1/search.php?f=";
    public static final String mealNameRecipeURL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_search, container, false);
        //calling init method to initialize all the variables and widgets
        init();
        return v;
    }

    //initializing all the variables and widgets
    //calling recipes list based on search view query
    private void init() {
        recipeList = v.findViewById(R.id.search_list_view);
        searchView = v.findViewById(R.id.search_view);
        searchPB = v.findViewById(R.id.search_progressbar);
        searchTitle = v.findViewById(R.id.search_title_textView);

        //Using volley to get data from http api and for the request queue created
        requestQueue = Volley.newRequestQueue(getActivity());
        //search array list initialized to add all the results
        searchDataArrayList = new ArrayList<SearchData>();

        //checking is searchview query is empty if it is then getting top dishes by calling method
        if(searchView.getQuery().toString().trim().equals("")){
            getTopDishes();
        }

        //using query text listener getting query string and sending it to other methods to fetch the
        //data
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //clearing array list so that old results dont get appended
                searchDataArrayList.clear();
                //if query length is 1 then calling different url using which it get all results which
                //are starts from that one letter
                if(s.length() == 1){
                    getDish(firstLetterRecipeURL+s);
                }
                //if query length is greater than 1 then using different url to get the results
                else if(s.length() > 1) {
                    getDish(mealNameRecipeURL+s);
                }
                //if query length is 0 then calling default url and getting 5 random dishes
                else if(s.length() == 0){
                    getTopDishes();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                //clearing array list so that old results dont get appended
                searchDataArrayList.clear();
                //if query length is 1 then calling different url using which it get all results which
                //are starts from that one letter
                if(s.length() == 1){
                    getDish(firstLetterRecipeURL+s);
                }
                //if query length is greater than 1 then using different url to get the results
                else if(s.length() > 1){
                    getDish(mealNameRecipeURL+s);
                }
                //if query length is 0 then calling default url and getting 5 random dishes
                else if(s.length() == 0) {
                    getTopDishes();
                }
                return true;
            }
        });

    }

    //calling getDish method 5 times to get 5 random dishes
    private void getTopDishes() {
        for(int i=0;i<5;i++){
            getDish(randomRecipeURL);
        }
    }

    //getDish method getting data from Url using Volley and callback method
    //setting progress bar visible while loading result
    public void getDish(String RecipeURL){
        searchPB.setVisibility(View.VISIBLE);

        //inside requestQueue calling FetchData.getRequest method to get the url response and extracting
        //data from that response and setting it up in searchData class and then adding the data into the
        //search array list then creating new adapter and setting it into recipeList
        requestQueue.add(FetchData.getRequest(RecipeURL, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    recipeList.setAdapter(null);
                    searchPB.setVisibility(View.GONE);
                    searchTitle.setText("Top Results");
                    JSONObject responseData = new JSONObject(result);
                    JSONArray mealsArray = responseData.getJSONArray("meals");
                    for (int i = 0; i < mealsArray.length(); i++) {
                        JSONObject meal = mealsArray.getJSONObject(i);
                        SearchData randomDish = new SearchData(meal.getString("strMeal"),
                                meal.getString("strCategory"),meal.getString("strMealThumb"));
                        searchDataArrayList.add(randomDish);
                    }
                    SearchRecipeListAdapter searchRecipeListAdapter = new SearchRecipeListAdapter(getActivity(),searchDataArrayList);
                    recipeList.setAdapter(searchRecipeListAdapter);

                } catch (JSONException e) {
                    //if there is no response then setting null adapter in recipeList and chaning
                    //search title text
                    searchTitle.setText("No Results");
                    recipeList.setAdapter(null);
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