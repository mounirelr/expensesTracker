package com.example.expensestracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensestracker.model.ExpenseWithWalletAndCategory;
import com.example.expensestracker.model.User;
import com.example.expensestracker.model.Wallet;
import com.example.expensestracker.service.ExpenseServiceImpl;
import com.example.expensestracker.service.WalletServiceImpl;

import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
     ExpenseAdapter expenseAdapter;

    ExpenseServiceImpl expenseService;
    WalletServiceImpl walletService;
    User user;
    LinearLayout addExpense, profilButton  ,categoryButton,budgetSection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history);
        recyclerView = findViewById(R.id.recyclerViewHistory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        expenseService=new ExpenseServiceImpl(this);
        walletService=new WalletServiceImpl(this);

        addExpense=findViewById(R.id.addExpense);
        profilButton=findViewById(R.id.profilButton);
        categoryButton=findViewById(R.id.categoryButton);
        budgetSection = findViewById(R.id.budgetSection);


        user = getIntent().getParcelableExtra("userData");
        if (user == null) {
            Toast.makeText(this, "User data missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        Wallet walletUser = walletService.getWalletByUserId(user.getId());


        List<ExpenseWithWalletAndCategory> expenses =expenseService.getAllExpense(walletUser.getIdWallet());
        expenseAdapter = new ExpenseAdapter(expenses);
        recyclerView.setAdapter(expenseAdapter);

        addExpense.setOnClickListener(v->{
            Intent intent = new Intent(this,addExpenseActivity.class);
            intent.putExtra("userData",user);
            startActivity(intent);
            finish();
        });

        profilButton.setOnClickListener(v->{
            Intent intent = new Intent(this,ProfilActivity.class);
            intent.putExtra("userData",user);
            startActivity(intent);
            finish();
        });

        categoryButton.setOnClickListener(v->{
            Intent intent = new Intent(this,CategoriesActivity.class);
            intent.putExtra("userData",user);
            startActivity(intent);
            finish();
        });

        budgetSection.setOnClickListener(v -> {
            Intent intent = new Intent(this, BudgetActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
        });









        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}