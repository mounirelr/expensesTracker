package com.example.expensestracker.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Wallet.class,
                parentColumns = "idWallet",
                childColumns = "idWallet",
                onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Category.class,
                parentColumns = "idCategory",
                childColumns = "idCategory",
                onDelete = ForeignKey.CASCADE)
})
public class Expense {
    @PrimaryKey(autoGenerate = true)
    private  int idExpense;
    private String title;

    private String description;

    private double amount;
    private  String date;

    private int idWallet;
    private int idCategory;



    public Expense(){

    }

    public Expense(int idExpense,String title,String description,double amount,String date,int idWallet,int idCategory){
        this.idExpense=idExpense;
        this.title=title;
        this.description=description;
        this.amount=amount;
        this.date=date;
        this.idWallet=idWallet;
        this.idCategory=idCategory;
    }

    public Expense(String title,String description,double amount,String date,int idWallet,int idCategory){
        this.title=title;
        this.description=description;
        this.amount=amount;
        this.date=date;
        this.idWallet=idWallet;
        this.idCategory=idCategory;
    }


    public int getIdExpense() {
        return idExpense;
    }

    public void setIdExpense(int idExpense) {
        this.idExpense = idExpense;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getIdWallet() {
        return idWallet;
    }

    public void setIdWallet(int idWallet) {
        this.idWallet = idWallet;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
}
