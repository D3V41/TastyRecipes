package com.deval.tastyrecipes.models;

public class Recipe {
    private String recipeName,recipeCategory,recipeInstruction,recipeUrl,recipeTags,recipeYoutubeUrl;
    private String[] recipeIngredients,recipeIngredientsMeasure;


    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public String getRecipeInstruction() {
        return recipeInstruction;
    }

    public void setRecipeInstruction(String recipeInstruction) {
        this.recipeInstruction = recipeInstruction;
    }

    public String getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(String recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    public String getRecipeTags() {
        return recipeTags;
    }

    public void setRecipeTags(String recipeTags) {
        this.recipeTags = recipeTags;
    }

    public String getRecipeYoutubeUrl() {
        return recipeYoutubeUrl;
    }

    public void setRecipeYoutubeUrl(String recipeYoutubeUrl) {
        this.recipeYoutubeUrl = recipeYoutubeUrl;
    }

    public String[] getRecipeIngredients() {
        return recipeIngredients;
    }

    public void setRecipeIngredients(String[] recipeIngredients) {
        this.recipeIngredients = recipeIngredients;
    }

    public String[] getRecipeIngredientsMeasure() {
        return recipeIngredientsMeasure;
    }

    public void setRecipeIngredientsMeasure(String[] recipeIngredientsMeasure) {
        this.recipeIngredientsMeasure = recipeIngredientsMeasure;
    }
}
