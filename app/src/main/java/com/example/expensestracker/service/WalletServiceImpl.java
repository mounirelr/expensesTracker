package com.example.expensestracker.service;

import android.content.Context;

import com.example.expensestracker.AppDatabase;
import com.example.expensestracker.DAO.ExpenseDao;
import com.example.expensestracker.DAO.WalletDao;
import com.example.expensestracker.model.Wallet;

import java.util.concurrent.Executors;

public class WalletServiceImpl implements WalletService {
    private Context context;
   public WalletServiceImpl(Context context){
       this.context=  context;

   }

    @Override
    public void createWallet(Wallet newWallet) {
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        WalletDao walletDao = databaseInstance.walletDao();
            walletDao.insert(newWallet);

    }

    public Wallet getWalletByUserId(int userId){
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        WalletDao walletDao = databaseInstance.walletDao();
         return walletDao.getWalletByUserId(userId);

    }



    @Override
    public void update(Wallet wallet){
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        WalletDao walletDao = databaseInstance.walletDao();
        walletDao.update(wallet);

    }


    @Override
    public void clearWalletTable() {
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        WalletDao walletDao = databaseInstance.walletDao();
            walletDao.clearWalletTable();

    }
}
