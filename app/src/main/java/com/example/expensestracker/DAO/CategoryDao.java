package com.example.expensestracker.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.expensestracker.model.Category;
import com.example.expensestracker.model.Wallet;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Category category);

    @Query("SELECT * FROM Category")
    List<Category> getAllCategories();

    @Query("DELETE FROM Category")
    void clearWalletTable();
}
