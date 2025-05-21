package com.example.expensestracker.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class ExpenseWithWalletAndCategory {
    // this class represent a combined data structure that join related information of expense ,category,wallet table to get in one query all information about this relations
    @Embedded
    public Expense expense;
    @Relation(
            parentColumn = "idWallet",
            entityColumn = "idWallet"
    )
    public Wallet wallet;


    @Relation(
            parentColumn = "idCategory",
            entityColumn = "idCategory"
    )
    public Category category;
}
