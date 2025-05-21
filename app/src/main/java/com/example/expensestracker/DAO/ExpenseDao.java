package com.example.expensestracker.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.expensestracker.model.Expense;
import com.example.expensestracker.model.ExpenseWithWalletAndCategory;

import java.util.List;
@Dao
public interface ExpenseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)

    void insert(Expense expense);


    @Query("DELETE FROM Expense")
    void clearExpenseTable();

    @Transaction
    @Query("SELECT * FROM Expense WHERE idWallet = :idWallet ORDER BY idExpense DESC")
    List<ExpenseWithWalletAndCategory> getAllExpensesWithDetails(int idWallet);
}
