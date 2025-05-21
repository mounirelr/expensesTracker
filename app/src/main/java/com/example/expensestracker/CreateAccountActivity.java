package com.example.expensestracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensestracker.DAO.WalletDao;
import com.example.expensestracker.model.User;
import com.example.expensestracker.model.Wallet;
import com.example.expensestracker.service.AuthServiceVolley;
import com.example.expensestracker.service.WalletServiceImpl;

import java.util.concurrent.Executors;

public class CreateAccountActivity extends AppCompatActivity {

    private static final String TAG = "CreateAccountActivity";

    private EditText firstNameInput, lastNameInput, emailInput, passwordInput;
    private Spinner currencySpinner;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);


        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        registerButton = findViewById(R.id.registerButton);
        currencySpinner = findViewById(R.id.currency_spinner);
        WalletServiceImpl walletService = new WalletServiceImpl(this);


        String[] currencies = {"USD", "MAD", "EURO"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Register button clicked");


                String firstName = firstNameInput.getText().toString().trim();
                String lastName = lastNameInput.getText().toString().trim();
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();
                String currency = currencySpinner.getSelectedItem().toString();


                if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(CreateAccountActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }


                User newUser = new User(firstName, lastName, email, password, currency);


                AuthServiceVolley.registerUser(CreateAccountActivity.this, newUser, new AuthServiceVolley.RegisterCallback() {
                    @Override
                    public void onSuccess(String status, String message ,int idUser) {
                        Log.d(TAG, "Success: " + message);
                        Toast.makeText(CreateAccountActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();

                        Wallet wallet = new Wallet();
                        wallet.setBudget(0);
                        wallet.setAmountSpend(0);
                        wallet.setCurrency(currency);
                        wallet.setIdUser(idUser);
                        walletService.createWallet(wallet);


                        finish();
                    }

                    @Override
                    public void onError(String error) {
                        Log.e(TAG, "Error: " + error);
                        Toast.makeText(CreateAccountActivity.this, "Registration failed: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
