package com.example.expensestracker.service;

import android.content.Context;
import android.util.Log;

import com.example.expensestracker.AppDatabase;
import com.example.expensestracker.DAO.ExpenseDao;
import com.example.expensestracker.model.Expense;
import com.example.expensestracker.model.ExpenseWithWalletAndCategory;
import com.example.expensestracker.model.Wallet;

import java.util.List;

public class ExpenseServiceImpl implements ExpenseService {

    private Context context;


    public ExpenseServiceImpl(Context context){
        this.context=context;
    }
    @Override
    public void addExpense(Expense newExpense) {
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        ExpenseDao expenseDao = databaseInstance.expenseDao();
        expenseDao.insert(newExpense);
    }

    @Override
    public void clearExpensetTable() {
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        ExpenseDao expenseDao = databaseInstance.expenseDao();
        expenseDao.clearExpenseTable();

    }



    @Override
    public List<ExpenseWithWalletAndCategory> getAllExpense(int idWallet) {
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        ExpenseDao expenseDao = databaseInstance.expenseDao();
        List<ExpenseWithWalletAndCategory> expenses = expenseDao.getAllExpensesWithDetails(idWallet);
        Log.d("ExpenseService", "Number of expenses: " + expenses.size());
        return   expenses;

    }
}
