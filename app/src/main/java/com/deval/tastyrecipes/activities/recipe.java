package com.deval.tastyrecipes.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.deval.tastyrecipes.R;
import com.deval.tastyrecipes.helpers.FetchData;
import com.deval.tastyrecipes.interfaces.VolleyCallback;
import com.deval.tastyrecipes.models.FavouriteRecipe;
import com.deval.tastyrecipes.models.Recipe;
import com.deval.tastyrecipes.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Map;

public class recipe extends AppCompatActivity {

    ImageView recipeImage;
    TextView recipeName, recipeCategory, paraInstruction;
    LinearLayout tags, ingredients, measures;
    ImageView favouriteIcon;
    Context _context;
    //setting up apis
    public static final String recipeByName = "https://www.themealdb.com/api/json/v1/1/search.php?s=";
    RequestQueue requestQueue;
    FirebaseUser user;
    DatabaseReference reference;

    String recipeNameToSearch, userId, favouriteRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        _context = this;
        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null) {
            recipeNameToSearch = b.getString("recipeTitle");
        }
        init();
    }

    //Initialization
    private void init() {
        requestQueue = Volley.newRequestQueue(recipe.this);
        recipeImage = findViewById(R.id.recipeImage);
        recipeName = findViewById(R.id.recipeName);
        recipeCategory = findViewById(R.id.recipeCategory);
        paraInstruction = findViewById(R.id.paraInstruction);
        tags = findViewById(R.id.tags);
        ingredients = findViewById(R.id.ingredients);
        measures = findViewById(R.id.measure);
        favouriteIcon = findViewById(R.id.favouriteIcon);
        reference = FirebaseDatabase.getInstance().getReference("Favourites");
        user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        favouriteIcon.setTag(1);
        setClickListener(favouriteIcon);
        getRecipe(recipeNameToSearch);
    }

    private void checkRecipeLiked() {
        Query likedRecipe = reference.orderByChild("favouriteRecipeName").equalTo(recipeName.getText().toString());
        Log.i("Recipe", recipeName.getText().toString());
        likedRecipe.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("here", snapshot + "");
                for (DataSnapshot recipeSnapshot : snapshot.getChildren()) {
                    if (recipeSnapshot != null) {
                        toggleIcon();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(_context, "Error removing favourites", Toast.LENGTH_SHORT).show();
            }
        });
    }


    //Set Listener
    private void setClickListener(ImageView favouriteIcon) {
        favouriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToFavourites();
                toggleIcon();
            }
        });
    }

    private void addToFavourites() {
        if ((int) favouriteIcon.getTag() == 1) {
            //add to favourites
            FavouriteRecipe fr = new FavouriteRecipe();
            favouriteRecipe = recipeName.getText().toString();
            fr.setFavouriteRecipeName(favouriteRecipe);
            fr.setUserId(userId);


            reference.push().setValue(fr).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(_context, "Added", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(_context, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            //remove from favourites
            Query removeFavourites = reference.orderByChild("favouriteRecipeName").equalTo(recipeName.getText().toString());

            removeFavourites.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.i("delete", snapshot + "");
                    for (DataSnapshot recipeSnapshot : snapshot.getChildren()) {
                        recipeSnapshot.getRef().removeValue();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(_context, "Error removing favourites", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    private void toggleIcon() {
        if ((int) favouriteIcon.getTag() == 1) {
            favouriteIcon.setImageResource(R.drawable.ic_baseline_favorite_24);
            favouriteIcon.setTag(2);
        } else {
            favouriteIcon.setImageResource(R.drawable.ic_baseline_favorite_border_24);
            favouriteIcon.setTag(1);
        }
    }

    //Get Recipe
    private void getRecipe(String recipeNameToSearch) {
        requestQueue.add(FetchData.getRequest(recipeByName + recipeNameToSearch, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject responseData = new JSONObject(result);
                    JSONArray mealsArray = responseData.getJSONArray("meals");
                    Recipe recipeCreate = createRecipeObject(mealsArray.getJSONObject(0));
                    populateView(recipeCreate);
                    checkRecipeLiked();


//                    Log.i("Data",recipeCreate.getRecipeTags()+"");
//                    for(int i=0;i<20;i++) {
//                        Log.i("Data",recipeCreate.getRecipeIngredients()[i]+"");
//                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                Toast.makeText(recipe.this, result, Toast.LENGTH_SHORT).show();
            }
        }));
    }

    //Initialize Views
    private void populateView(Recipe recipe) {
        Picasso.get().load(recipe.getRecipeUrl()).into(recipeImage);
        recipeName.setText(recipe.getRecipeName());
        recipeCategory.setText(recipe.getRecipeCategory());
        paraInstruction.setText(recipe.getRecipeInstruction());

        setRecipeTags(recipe.getRecipeTags());
        populateIngredientsAndMeasure(recipe.getRecipeIngredients(), recipe.getRecipeIngredientsMeasure());
    }

    private void setRecipeTags(String recipeTags) {
        if (recipeTags.compareTo("") != 0 && recipeTags.compareTo("null") != 0) {
            String[] tagsStrings = recipeTags.split(",");
            for (String tagsString : tagsStrings) {
                TextView tag = new TextView(this);
                tag.setText(tagsString);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 20, 0);
                tag.setLayoutParams(params);
                tag.setPadding(20, 10, 20, 10);
                tag.setBackgroundColor(ContextCompat.getColor(this, R.color.pink_50));
                tags.addView(tag);
            }
        }
    }

    private void populateIngredientsAndMeasure(String[] recipeIngredients, String[] recipeMeasures) {
        for (String recipeIngredient : recipeIngredients) {
            if (recipeIngredient.compareTo("null") != 0 && recipeIngredient.compareTo("") != 0 && recipeIngredient.compareTo(" ") != 0) {
                TextView ingredient = new TextView(this);
                ingredient.setText(recipeIngredient);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(10, 10, 20, 10);
                ingredient.setLayoutParams(params);
                ingredient.setPadding(20, 10, 20, 10);
                ingredient.setBackgroundColor(ContextCompat.getColor(this, R.color.pink_100));
                ingredients.addView(ingredient);
            }
        }
        for (String recipeMeasure : recipeMeasures) {
            if (recipeMeasure.compareTo("null") != 0 && recipeMeasure.compareTo("") != 0 && recipeMeasure.compareTo(" ") != 0) {
                TextView measure = new TextView(this);
                measure.setText(recipeMeasure);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(10, 10, 20, 10);
                measure.setLayoutParams(params);
                measure.setPadding(20, 10, 20, 10);
                measure.setBackgroundColor(ContextCompat.getColor(this, R.color.pink_100));
                measures.addView(measure);
            }
        }
    }

    //Create Recipe Object
    private Recipe createRecipeObject(JSONObject recipe) {
        Recipe recipeObj = new Recipe();
        try {
            recipeObj.setRecipeName(recipe.getString("strMeal"));
            recipeObj.setRecipeCategory(recipe.getString("strCategory"));
            recipeObj.setRecipeInstruction(recipe.getString("strInstructions"));
            recipeObj.setRecipeUrl(recipe.getString("strMealThumb"));
            recipeObj.setRecipeTags(recipe.getString("strTags"));
            recipeObj.setRecipeYoutubeUrl(recipe.getString("strYoutube"));
            String[] ingredients = new String[20];
            String[] measures = new String[20];
            for (int i = 0; i < 20; i++) {
                int index = i + 1;
                ingredients[i] = recipe.getString("strIngredient" + index);
            }
            recipeObj.setRecipeIngredients(ingredients);
            for (int i = 0; i < 20; i++) {
                int index = i + 1;
                measures[i] = recipe.getString("strMeasure" + index);
            }
            recipeObj.setRecipeIngredientsMeasure(measures);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return recipeObj;
    }
}