package com.example.expensestracker;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensestracker.model.User;

public class HomeActivity extends AppCompatActivity {
    TextView welcomeMsg;
    LinearLayout budgetButton;
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        welcomeMsg = findViewById(R.id.welcomeMessage);
        User user = getIntent().getParcelableExtra("userData", User.class);

        if (user != null) {
            welcomeMsg.setText("Hello " +user.getFirstName()+" "+user.getLastName() +" " +user.getId());

        }

        budgetButton=findViewById(R.id.budgetSection);
        budgetButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, BudgetActivity.class);
            startActivity(intent);
        });

    }
}