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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    //declaring widgets and variables
    ListView categoriesList;
    TextView categoriesTitle;
    View v;
    RequestQueue requestQueue;

    //setting up apis
    public static final String categoriesURL = "https://www.themealdb.com/api/json/v1/1/categories.php";

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        //calling init method to initialize all the variables and widgets
        init();
        return v;
    }

    //initializing all the variables and widgets
    //calling recipes list based on search view query
    private void init() {
        categoriesList = v.findViewById(R.id.categories_lv);
        categoriesTitle = v.findViewById(R.id.categories_title_tv);

        //Using volley to get data from http api and for the request queue created
        requestQueue = Volley.newRequestQueue(getActivity());

        getCategories(categoriesURL);

    }

    //getDish method getting data from Url using Volley and callback method
    //setting progress bar visible while loading result
    public void getCategories(String RecipeURL){

        //inside requestQueue calling FetchData.getRequest method to get the url response and extracting
        //data from that response and setting it up in searchData class and then adding the data into the
        //search array list then creating new adapter and setting it into recipeList
        requestQueue.add(FetchData.getRequest(RecipeURL, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    categoriesList.setAdapter(null);
                    categoriesTitle.setText("Categories");
                    JSONObject responseData = new JSONObject(result);
                    JSONArray categoriesArray = responseData.getJSONArray("categories");
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject category = categoriesArray.getJSONObject(i);

                    }
//                    SearchRecipeListAdapter searchRecipeListAdapter = new SearchRecipeListAdapter(getActivity(),searchDataArrayList);
//                    categoriesList.setAdapter(searchRecipeListAdapter);
                } catch (JSONException e) {
                    //if there is no response then setting null adapter in recipeList and chaning
                    //search title text
                    categoriesTitle.setText("No Results");
                    categoriesList.setAdapter(null);
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