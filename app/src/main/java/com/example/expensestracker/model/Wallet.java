package com.example.expensestracker.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Wallet {
    @PrimaryKey(autoGenerate = true)
    private int idWallet;
    private double budget;

    private double amountSpend;
    private String currency;
    private int idUser;

    public Wallet(){

    }
    public Wallet(int idWallet,double budget,double amountSpend,String currency,int idUser){
        this.idWallet=idWallet;
        this.budget=budget;
        this.amountSpend=amountSpend;
        this.currency=currency;
        this.idUser=idUser;
    }
    public Wallet(double budget,double amountSpend,String currency,int idUser){

        this.budget=budget;
        this.amountSpend=amountSpend;
        this.currency=currency;
        this.idUser=idUser;
    }


    public int getIdWallet() {
        return idWallet;
    }

    public void setIdWallet(int idWallet) {
        this.idWallet = idWallet;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public double getAmountSpend() {
        return amountSpend;
    }

    public void setAmountSpend(double amountSpend) {
        this.amountSpend = amountSpend;
    }
}
