package com.example.expensestracker.DAO;
import com.example.expensestracker.model.Wallet;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;



@Dao
public interface WalletDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Wallet wallet);

    @Update
    void update(Wallet wallet);
    @Query("DELETE FROM Wallet")
    void clearWalletTable();

    @Query("SELECT * FROM wallet WHERE idUser = :userId LIMIT 1")
    Wallet getWalletByUserId(int userId);
}

