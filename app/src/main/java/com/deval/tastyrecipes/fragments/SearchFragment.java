package com.deval.tastyrecipes.fragments;

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
import com.deval.tastyrecipes.models.SearchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    ListView recipeList;
    SearchView searchView;
    ProgressBar searchPB;
    TextView searchTitle;
    View v;

    ArrayList<SearchData> searchDataArrayList;
    RequestQueue requestQueue;
    
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
        init();
        return v;
    }

    private void init() {
        recipeList = v.findViewById(R.id.search_list_view);
        searchView = v.findViewById(R.id.search_view);
        searchPB = v.findViewById(R.id.search_progressbar);
        searchTitle = v.findViewById(R.id.search_title_textView);

        requestQueue = Volley.newRequestQueue(getActivity());
        searchDataArrayList = new ArrayList<SearchData>();

        if(searchView.getQuery().toString().trim().equals("")){
            getTopDishes();
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchDataArrayList.clear();
                if(s.length() == 1){
                    getDish(firstLetterRecipeURL+s);
                } else if(s.length() > 1) {
                    getDish(mealNameRecipeURL+s);
                } else {
                    getTopDishes();
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                searchDataArrayList.clear();
                if(s.length() == 1){
                    getDish(firstLetterRecipeURL+s);
                } else if(s.length() > 1){
                    getDish(mealNameRecipeURL+s);
                } else {
                    getTopDishes();
                }
                return true;
            }
        });

    }

    private void getTopDishes() {
        for(int i=0;i<5;i++){
            getDish(randomRecipeURL);
        }
    }

    public void getDish(String RecipeURL){
        searchPB.setVisibility(View.VISIBLE);

        requestQueue.add(FetchData.getRequest(RecipeURL, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
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
                    searchTitle.setText("No Results");
                    recipeList.setAdapter(null);
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        }));
    }


}