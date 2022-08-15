package com.deval.tastyrecipes.models;

public class CategoryRecipe {
    private int recipeId;
    private String recipeTitle;
    private String recipeImage;

    public CategoryRecipe(int recipeId, String recipeTitle, String recipeImage) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.recipeImage = recipeImage;
    }

    public int getRecipeId() { return recipeId; }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

}
