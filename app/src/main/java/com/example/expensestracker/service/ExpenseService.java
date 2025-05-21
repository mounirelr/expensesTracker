package com.example.expensestracker.service;

import androidx.room.Query;

import com.example.expensestracker.model.Expense;
import com.example.expensestracker.model.ExpenseWithWalletAndCategory;

import java.util.List;

public interface ExpenseService {


    void addExpense(Expense newExpense);

    public void clearExpensetTable();



    List<ExpenseWithWalletAndCategory> getAllExpense(int idWallet);
}
