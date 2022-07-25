package com.deval.tastyrecipes.models;

//search item data
public class SearchData {
    private String recipeTitle;
    private String recipeCategory;
    private String recipeImage;

    public SearchData(String recipeTitle, String recipeCategory, String recipeImage) {
        this.recipeTitle = recipeTitle;
        this.recipeCategory = recipeCategory;
        this.recipeImage = recipeImage;
    }

    public String getRecipeTitle() {
        return recipeTitle;
    }

    public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
    }

    public String getRecipeCategory() {
        return recipeCategory;
    }

    public void setRecipeCategory(String recipeCategory) {
        this.recipeCategory = recipeCategory;
    }

    public String getRecipeImage() {
        return recipeImage;
    }

    public void setRecipeImage(String recipeImage) {
        this.recipeImage = recipeImage;
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "recipeTitle='" + recipeTitle + '\'' +
                ", recipeCategory='" + recipeCategory + '\'' +
                ", recipeImage='" + recipeImage + '\'' +
                '}';
    }
}
