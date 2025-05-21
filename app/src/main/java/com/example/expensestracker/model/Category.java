package com.example.expensestracker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Category {
@PrimaryKey(autoGenerate = true)
    private int idCategory;
    private String categoryName;


    public Category(){

    }

    public Category(int idCategory,String categoryName){
        this.idCategory=idCategory;
        this.categoryName=categoryName;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return idCategory+" "+categoryName;
    }
}
