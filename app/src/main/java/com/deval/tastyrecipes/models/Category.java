package com.deval.tastyrecipes.models;

//This is the Category model class used to get and set the required variable of category
public class Category {
    private int categoryId;
    private String categoryTitle;
    private String categoryDescription;
    private String categoryImage;

    //This is the constructor setting values with the parameters passed
    public Category(int categoryId, String categoryTitle, String categoryDescription, String categoryImage) {
        this.categoryId = categoryId;
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
        this.categoryImage = categoryImage;
    }

    //This method is used to get the category id
    public  int getCategoryId() { return categoryId; }

    //This method is used to set the category id
    public  void setCategoryId(int categoryId) { this.categoryId = categoryId; }

    //This method is used to get the category title
    public String getCategoryTitle() {
        return categoryTitle;
    }

    //This method is used to set the category title
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    //This method is used to get the category description
    public String getCategoryDescription() {
        return categoryDescription;
    }

    //This method is used to set the category description
    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    //This method is used to get the category image
    public String getCategoryImage() {
        return categoryImage;
    }

    //This method is used to set the category image
    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

}
