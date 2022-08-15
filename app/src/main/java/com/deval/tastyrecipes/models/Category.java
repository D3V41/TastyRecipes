package com.deval.tastyrecipes.models;

public class Category {
        private int categoryId;
        private String categoryTitle;
        private String categoryDescription;
        private String categoryImage;

        public Category(int categoryId, String categoryTitle, String categoryDescription, String categoryImage) {
            this.categoryId = categoryId;
            this.categoryTitle = categoryTitle;
            this.categoryDescription = categoryDescription;
            this.categoryImage = categoryImage;
        }

        public  int getCategoryId() { return categoryId; }

    public  void setCategoryId(int categoryId) { this.categoryId = categoryId; }

        public String getCategoryTitle() {
            return categoryTitle;
        }

        public void setCategoryTitle(String categoryTitle) {
            this.categoryTitle = categoryTitle;
        }

        public String getCategoryDescription() {
            return categoryDescription;
        }

        public void setCategoryDescription(String categoryDescription) {
            this.categoryDescription = categoryDescription;
        }

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

}
