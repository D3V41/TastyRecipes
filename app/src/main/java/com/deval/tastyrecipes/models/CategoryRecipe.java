package com.deval.tastyrecipes.models;

//This is the Recipe model class used to get and set the required variable of Recipe of the selected category
public class CategoryRecipe {
    private int recipeId;
    private String recipeTitle;
    private String recipeImage;

    //This is the constructor setting values with the parameters passed
    public CategoryRecipe(int recipeId, String recipeTitle, String recipeImage) {
        this.recipeId = recipeId;
        this.recipeTitle = recipeTitle;
        this.recipeImage = recipeImage;
    }

    //This method is used to get the recipe id
    public int getRecipeId() { return recipeId; }

    //This method is used to set the recipe id
    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    //This method is used to get the recipe title
    public String getRecipeTitle() {
        return recipeTitle;
    }

    //This method is used to set the recipe title
    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    //This method is used to get the recipe image
    public String getRecipeImage() {
        return recipeImage;
    }

    //This method is used to set the recipe image
    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

}
