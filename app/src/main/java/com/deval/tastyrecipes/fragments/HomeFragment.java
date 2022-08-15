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
import com.deval.tastyrecipes.adapters.CategoryAdapter;
import com.deval.tastyrecipes.adapters.SearchRecipeListAdapter;
import com.deval.tastyrecipes.helpers.FetchData;
import com.deval.tastyrecipes.interfaces.VolleyCallback;
import com.deval.tastyrecipes.models.Category;
import com.deval.tastyrecipes.models.SearchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
     This is a Home Fragment in which we show the list of categories available in the tasty recipes app
 */
public class HomeFragment extends Fragment {

    //declaring widgets and variables
    ListView categoriesList;
    TextView categoriesTitle;
    View v;
    RequestQueue requestQueue;
    ArrayList<Category> categoryList;

    //setting up apis
    public static final String categoriesURL = "https://www.themealdb.com/api/json/v1/1/categories.php";

    //Empty constructor
    public HomeFragment() {
        // Required empty public constructor
    }

    //This is the On create method that calls its parent constructor
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //This is the On create view method that inflates the fragment_home xml view file to be used here and calls init method
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        //calling init method to initialize all the variables and widgets
        init();
        return v;
    }

    //initializing all the variables and widgets
    //calling getCategories method based on the categories URL
    private void init() {
        categoriesList = v.findViewById(R.id.categories_lv);
        categoriesTitle = v.findViewById(R.id.categories_title_tv);
        categoryList = new ArrayList<Category>();

        //Using volley to get data from http api and for the request queue created
        requestQueue = Volley.newRequestQueue(getActivity());

        getCategories(categoriesURL);

    }

    //getCategories method getting Categories list from Url using Volley and callback method
    public void getCategories(String CategoryURL){

        //inside requestQueue calling FetchData.getRequest method to get the url response and extracting
        //data from that response and setting it up in Category class and then adding the data into the
        //category array list then creating new adapter and setting it into categories list
        requestQueue.add(FetchData.getRequest(CategoryURL, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    categoriesList.setAdapter(null);
                    categoriesTitle.setText("Categories");
                    JSONObject responseData = new JSONObject(result);
                    JSONArray categoriesArray = responseData.getJSONArray("categories");
                    for (int i = 0; i < categoriesArray.length(); i++) {
                        JSONObject category = categoriesArray.getJSONObject(i);
                        Category recipeCat = new Category(category.getInt("idCategory"),category.getString("strCategory"),
                                category.getString("strCategoryDescription"),category.getString("strCategoryThumb"));
                        categoryList.add(recipeCat);

                    }
                    CategoryAdapter categoriesListAdapter = new CategoryAdapter(getActivity(),categoryList);
                    categoriesList.setAdapter(categoriesListAdapter);
                } catch (JSONException e) {
                    //if there is no response then setting null adapter in categories list and chaning
                    //categories title text
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