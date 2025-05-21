package com.example.expensestracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.expensestracker.model.Category;
import com.example.expensestracker.model.Expense;
import com.example.expensestracker.model.User;
import com.example.expensestracker.model.Wallet;
import com.example.expensestracker.service.CategoryServiceImpl;
import com.example.expensestracker.service.ExpenseServiceImpl;
import com.example.expensestracker.service.WalletServiceImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class addExpenseActivity extends AppCompatActivity {

    Spinner categoryList;
    EditText editTextDate, titleInput, descriptionInput, editTextAmount;
    LinearLayout budgetSection , profilButton,historyButton,categoryButton;
    Button addButton;

    CategoryServiceImpl categoryService;
    WalletServiceImpl walletService;
    ExpenseServiceImpl expenseService;

    List<Category> categories = new ArrayList<>();
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // Initialize views
        categoryList = findViewById(R.id.spinnerCategory);
        editTextDate = findViewById(R.id.editTextDate);
        titleInput = findViewById(R.id.editTextTitle);
        descriptionInput = findViewById(R.id.editTextDescription);
        editTextAmount = findViewById(R.id.editTextAmount);
        budgetSection = findViewById(R.id.budgetSection);
        profilButton=findViewById(R.id.profilButton);
        addButton = findViewById(R.id.buttonAddExpense);
        historyButton=findViewById(R.id.historyButton);
        categoryButton=findViewById(R.id.categoryButton);

        // Initialize services
        categoryService = new CategoryServiceImpl(this);
        walletService = new WalletServiceImpl(this);
        expenseService = new ExpenseServiceImpl(this);

        // Load user
        user = getIntent().getParcelableExtra("userData");
        if (user == null) {
            Toast.makeText(this, "User data missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Populate spinner
        categories = categoryService.getAllCategories();
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryList.setAdapter(adapter);

        // Set date picker
        editTextDate.setOnClickListener(v -> showDatePickerDialog());

        // Navigate to budget screen
        budgetSection.setOnClickListener(v -> {
            Intent intent = new Intent(this, BudgetActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
        });
        // Navigate to profil screen
        profilButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ProfilActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
        });

        historyButton.setOnClickListener(v->{
            Intent intent = new Intent(this,HistoryActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);

        });


        categoryButton.setOnClickListener(v->{
            Intent intent = new Intent(this,CategoriesActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);

        });

        // Handle expense submission
        addButton.setOnClickListener(v -> addExpense());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(this, (view, year1, month1, day1) -> {
            String selectedDate = day1 + "/" + (month1 + 1) + "/" + year1;
            editTextDate.setText(selectedDate);
        }, year, month, day);

        dialog.show();
    }

    private void addExpense() {
        String title = titleInput.getText().toString().trim();
        String description = descriptionInput.getText().toString().trim();
        String date = editTextDate.getText().toString().trim();
        String amountStr = editTextAmount.getText().toString().trim();

        if (title.isEmpty() || date.isEmpty() || amountStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount format", Toast.LENGTH_SHORT).show();
            return;
        }

        Category selectedCategory = (Category) categoryList.getSelectedItem();
        if (selectedCategory == null) {
            Toast.makeText(this, "Please select a category", Toast.LENGTH_SHORT).show();
            return;
        }

        Wallet walletUser = walletService.getWalletByUserId(user.getId());
        if (walletUser == null) {
            Toast.makeText(this, "Wallet not found for user", Toast.LENGTH_SHORT).show();
            return;
        }
        double newSpend = amount+walletUser.getAmountSpend();
        if(newSpend>walletUser.getBudget()){
            Toast.makeText(this, "Insuffisant funds", Toast.LENGTH_SHORT).show();
            return;
        }

        Expense expense = new Expense();
        expense.setTitle(title);
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setDate(date);
        expense.setIdWallet(walletUser.getIdWallet());
        expense.setIdCategory(selectedCategory.getIdCategory());
        walletUser.setAmountSpend(walletUser.getAmountSpend()+amount);

        walletService.update(walletUser);
        expenseService.addExpense(expense);
        Toast.makeText(this, "Expense added successfully!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, BudgetActivity.class);
        intent.putExtra("userData", user);
        startActivity(intent);
        finish();
    }
}
