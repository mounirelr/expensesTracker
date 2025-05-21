package com.example.expensestracker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensestracker.model.User;
import com.example.expensestracker.model.Wallet;
import com.example.expensestracker.service.WalletServiceImpl;

public class BudgetActivity extends AppCompatActivity {
    TextView welcomeMsg ,budgetLabel,spendLabel,resetLabel ;
    ProgressBar spendProgressBar,resetProgressBar;
    WalletServiceImpl walletService ;
    Button resetButton;
    LinearLayout addExpense, profilButton ,historyButton ,categoryButton;

    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_budget);
        User user = getIntent().getParcelableExtra("userData", User.class);
        addExpense=findViewById(R.id.addExpense);
        profilButton=findViewById(R.id.profilButton);
        historyButton=findViewById(R.id.historyButton);
        categoryButton=findViewById(R.id.categoryButton);
        resetButton=findViewById(R.id.resetButton);

        welcomeMsg = findViewById(R.id.welcomeMessage);
        budgetLabel=findViewById(R.id.budgetLabel);
        spendLabel=findViewById(R.id.spendLabel);
        resetLabel=findViewById(R.id.resetLabel);
        spendProgressBar=findViewById(R.id.spendProgressBar);
        resetProgressBar=findViewById(R.id.resetProgressBar);

        walletService=new WalletServiceImpl(this);
        Wallet walletUser = walletService.getWalletByUserId(user.getId());

        double budget = walletUser.getBudget();
        double spendValue = walletUser.getAmountSpend();

        double restValue = budget-spendValue;
        String currency = walletUser.getCurrency();

        budgetLabel.setText("Budget: "+String.valueOf( budget) +" "+currency);
        spendLabel.setText("Spend: "+String.valueOf(spendValue)+" "+currency);
        int progressSpend = (int) ((spendValue*100)/budget);
        spendProgressBar.setProgress(progressSpend);

        resetLabel.setText("Rest: "+String.valueOf(restValue)+" "+currency);
        resetProgressBar.setProgress(100-progressSpend);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        addExpense.setOnClickListener(v->{
            Intent intent = new Intent(this,addExpenseActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
            finish();
        });

        profilButton.setOnClickListener(v->{
            Intent intent = new Intent(this,ProfilActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
            finish();
        });
        historyButton.setOnClickListener(v->{
            Intent intent = new Intent(this,HistoryActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
            finish();
        });
        categoryButton.setOnClickListener(v->{
            Intent intent = new Intent(this,CategoriesActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
            finish();
        });

        resetButton.setOnClickListener(v->{
            walletUser.setAmountSpend(0);
            walletService.update(walletUser);
            Intent intent = new Intent(this,BudgetActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
            finish();
        });



        if (user != null) {
            welcomeMsg.setText("Hello " +user.getFirstName()+" "+user.getLastName());

        }
    }
}