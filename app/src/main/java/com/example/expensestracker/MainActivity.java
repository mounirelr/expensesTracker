package com.example.expensestracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.expensestracker.DAO.WalletDao;
import com.example.expensestracker.model.Category;
import com.example.expensestracker.model.User;
import com.example.expensestracker.model.Wallet;

import com.example.expensestracker.service.AuthServiceVolley;
import com.example.expensestracker.service.CategoryService;
import com.example.expensestracker.service.CategoryServiceImpl;

import org.json.JSONObject;

import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private EditText emailInput, passwordInput ;
    TextView createAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        Button loginButton = findViewById(R.id.login_button);
        createAccount =findViewById(R.id.CreateAccountText);
        createAccount.setOnClickListener(v->{
            Intent intent = new Intent(this, CreateAccountActivity.class);
            startActivity(intent);
        });

        CategoryServiceImpl catService = new CategoryServiceImpl(this);
        Category c1 = new Category(1,"Food");
        Category c2 =new Category(2,"Education");
        Category c3 =new Category(3,"Invoice");
        Category c4 =new Category(4,"Health");
        Category c5 =new Category(5,"Other");
        catService.addCategory(c1);
        catService.addCategory(c2);
        catService.addCategory(c3);
        catService.addCategory(c4);
        catService.addCategory(c5);








        loginButton.setOnClickListener(v -> attemptLogin());

    }

    private void attemptLogin() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        AuthServiceVolley.loginUser(this, email, password, new AuthServiceVolley.LoginCallback() {
            @Override
            public void onSuccess(JSONObject userData) {
                try {
                    String welcomeMsg = "Welcome, " + userData.getString("firstName");
                    Toast.makeText(MainActivity.this, welcomeMsg, Toast.LENGTH_SHORT).show();
                    int id = Integer.parseInt(userData.getString("id"));
                    String firstName = userData.getString("firstName");
                    String lastName = userData.getString("lastName");
                    String email = userData.getString("email");

                    String currency = userData.getString("currency");

                    User user = new User(id,firstName,lastName,email,currency);

                    // Pass user data to HomeActivity
                    Intent intent = new Intent(MainActivity.this, BudgetActivity.class);
                    intent.putExtra("userData", user);
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    onError("Failed to parse user data: " + e.getMessage());
                }
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() ->
                        Toast.makeText(MainActivity.this, "Login failed: " + error, Toast.LENGTH_LONG).show()
                );
            }
        });
    }





}