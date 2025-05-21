package com.example.expensestracker;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.expensestracker.DAO.CategoryDao;
import com.example.expensestracker.DAO.ExpenseDao;
import com.example.expensestracker.DAO.WalletDao;
import com.example.expensestracker.model.Category;
import com.example.expensestracker.model.Expense;
import com.example.expensestracker.model.Wallet;

@Database(entities = {Wallet.class, Category.class, Expense.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract WalletDao walletDao();
    public abstract CategoryDao categoryDao();

    public abstract ExpenseDao expenseDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "expenses_tracker_db")
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}

