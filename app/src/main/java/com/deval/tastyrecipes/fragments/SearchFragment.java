package com.deval.tastyrecipes.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
    View v;

    ArrayList<SearchData> searchDataArrayList;
    RequestQueue requestQueue;


    public static final String randomRecipeURL = "https://www.themealdb.com/api/json/v1/1/random.php";


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

        searchDataArrayList = new ArrayList<SearchData>();

        requestQueue = Volley.newRequestQueue(getActivity());

        getTopDishes();

    }

    private void getTopDishes() {
        for(int i=0;i<5;i++){
            getRandomDish();
        }
    }

    public void getRandomDish(){
        searchPB.setVisibility(View.VISIBLE);
        requestQueue.add(FetchData.getRequest(randomRecipeURL, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    searchPB.setVisibility(View.GONE);
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
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
            }
        }));
    }


}