package com.example.expensestracker.service;

import com.example.expensestracker.model.Wallet;

public interface WalletService {

    public void createWallet(Wallet newWallet);
    public void clearWalletTable();

    public void update(Wallet wallet);

    Wallet getWalletByUserId(int userId);
}
