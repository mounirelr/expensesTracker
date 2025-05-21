package com.example.expensestracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensestracker.model.Category;
import com.example.expensestracker.model.User;
import com.example.expensestracker.model.Wallet;
import com.example.expensestracker.service.WalletServiceImpl;

public class ProfilActivity extends AppCompatActivity {
     Spinner currencySpinner;
     EditText editBudget;
     Button saveChanges;
    WalletServiceImpl walletService;
    LinearLayout addExpense,categoryButton,budgetSection,historyButton;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profil);

        walletService = new WalletServiceImpl(this);

        editBudget=findViewById(R.id.editBudget);
        saveChanges=findViewById(R.id.buttonSave);

        currencySpinner=findViewById(R.id.spinnerCurrency);

        user = getIntent().getParcelableExtra("userData");
        Wallet walletUser = walletService.getWalletByUserId(user.getId());

        editBudget.setText(String.valueOf(walletUser.getBudget()));



        String[] currencies = {"USD", "MAD", "EURO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);

        saveChanges.setOnClickListener(v->{
            double newBudget = Double.parseDouble(editBudget.getText().toString().trim());
            String selectedCurrency = currencySpinner.getSelectedItem().toString();
            walletUser.setBudget(newBudget);
            walletUser.setCurrency(selectedCurrency);
            walletService.update(walletUser);
            Intent intent = new Intent(this, BudgetActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
            finish();
        });




        budgetSection = findViewById(R.id.budgetSection);
        categoryButton=findViewById(R.id.categoryButton);

        historyButton=findViewById(R.id.historyButton);
        addExpense=findViewById(R.id.addExpense);


        budgetSection.setOnClickListener(v -> {
            Intent intent = new Intent(this, BudgetActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
        });

        addExpense.setOnClickListener(v->{
            Intent intent = new Intent(this,addExpenseActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);

        });

        categoryButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CategoriesActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
        });

        historyButton.setOnClickListener(v->{
            Intent intent = new Intent(this,HistoryActivity.class);
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