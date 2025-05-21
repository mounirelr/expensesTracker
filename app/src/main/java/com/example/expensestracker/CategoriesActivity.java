package com.example.expensestracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.expensestracker.model.ExpenseWithWalletAndCategory;
import com.example.expensestracker.model.User;
import com.example.expensestracker.model.Wallet;
import com.example.expensestracker.service.CategoryServiceImpl;
import com.example.expensestracker.service.ExpenseServiceImpl;
import com.example.expensestracker.service.WalletServiceImpl;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoriesActivity extends AppCompatActivity {
    LinearLayout budgetSection , profilButton,historyButton,addExpense;
    User user;
    ExpenseServiceImpl expenseService;
    CategoryServiceImpl categoryService;
    WalletServiceImpl walletService;
    TextView foodSpend,educationSpend,invoiceSpend,healthSpend,otherSpend;
    ProgressBar foodProgress,educationProgress,invoiceProgress,healthProgress,otherProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_categories);

        user = getIntent().getParcelableExtra("userData");
        if (user == null) {
            Toast.makeText(this, "User data missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }


        foodSpend=findViewById(R.id.categoryFoodSpend);
        educationSpend=findViewById(R.id.categoryEducationSpend);
        invoiceSpend=findViewById(R.id.categoryInvoiceSpend);
        healthSpend=findViewById(R.id.categoryHealthSpend);
        otherSpend=findViewById(R.id.categoryOtherSpend);

        foodProgress=findViewById(R.id.categoryProgressFood);
        educationProgress=findViewById(R.id.categoryProgressEducation);
        invoiceProgress=findViewById(R.id.categoryProgressInvoice);
        healthProgress=findViewById(R.id.categoryProgressHealth);
        otherProgress=findViewById(R.id.categoryProgressOther);

        expenseService=new ExpenseServiceImpl(this);
        walletService=new WalletServiceImpl(this);
        categoryService= new CategoryServiceImpl(this);
        Wallet walletUser = walletService.getWalletByUserId(user.getId());
        int idWalletUser = walletUser.getIdWallet();
        Double budget= walletUser.getBudget();

        Map<String,Double> totalSpendCategory =categoryService.getTotalSpendCategory(idWalletUser);


        foodSpend.setText("Spent: "+totalSpendCategory.get("food")+"  MAD of " +budget+" MAD");
        educationSpend.setText("Spent: "+totalSpendCategory.get("education")+"  MAD of " +budget+" MAD");
        invoiceSpend.setText("Spent: "+totalSpendCategory.get("invoice")+"  MAD of " +budget+" MAD");
        healthSpend.setText("Spent: "+totalSpendCategory.get("health")+"  MAD of " +budget+" MAD");
        otherSpend.setText("Spent: "+totalSpendCategory.get("other")+"  MAD of " +budget+" MAD");

        foodProgress.setProgress((int) ((totalSpendCategory.get("food")/budget)*100));
        educationProgress.setProgress((int) ((totalSpendCategory.get("education")/budget)*100));
        invoiceProgress.setProgress((int) ((totalSpendCategory.get("invoice")/budget)*100));
        healthProgress.setProgress((int) ((totalSpendCategory.get("health")/budget)*100));
        otherProgress.setProgress((int) ((totalSpendCategory.get("other")/budget)*100));






        budgetSection = findViewById(R.id.budgetSection);
        profilButton=findViewById(R.id.profilButton);

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



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}