package com.deval.tastyrecipes.models;

import java.util.Map;

public class FavouriteRecipe {
    private String userId,favouriteRecipeName;

    public FavouriteRecipe() {

    }
    public FavouriteRecipe(Map<String, String> map) {
        this.favouriteRecipeName = map.get("favouriteRecipeName");
        this.userId = map.get("userId");
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFavouriteRecipeName() {
        return favouriteRecipeName;
    }

    public void setFavouriteRecipeName(String favouriteRecipeName) {
        this.favouriteRecipeName = favouriteRecipeName;
    }
}
