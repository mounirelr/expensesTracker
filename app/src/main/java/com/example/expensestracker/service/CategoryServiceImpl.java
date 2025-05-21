package com.example.expensestracker.service;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.example.expensestracker.AppDatabase;
import com.example.expensestracker.DAO.CategoryDao;
import com.example.expensestracker.model.Category;
import com.example.expensestracker.model.ExpenseWithWalletAndCategory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class CategoryServiceImpl implements CategoryService {

    private Context context;
    private ExpenseServiceImpl expenseService;


    public CategoryServiceImpl(Context context){
        this.context=context;
    }
    @Override
    public void addCategory(Category newCategory) {
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        CategoryDao categoryDao = databaseInstance.categoryDao();

            categoryDao.insert(newCategory);


    }

    @Override
    public List<Category> getAllCategories() {
        AppDatabase db = AppDatabase.getInstance(context);
        CategoryDao categoryDao = db.categoryDao();

        return categoryDao.getAllCategories();
    }


    @Override
    public void clearTableCategory() {
        AppDatabase databaseInstance = AppDatabase.getInstance(context);
        CategoryDao categoryDao = databaseInstance.categoryDao();
        Executors.newSingleThreadExecutor().execute(() -> {
            categoryDao.clearWalletTable();
        });
    }


    public Map<String,Double> getTotalSpendCategory(int idWalletUser){

        Map<String ,Double> categoryTotalSpend = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        int currentMonth = cal.get(Calendar.MONTH) + 1;
        int currentYear = cal.get(Calendar.YEAR);
        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        expenseService=new ExpenseServiceImpl(context);


        List<ExpenseWithWalletAndCategory>  expensesUser= expenseService.getAllExpense(idWalletUser);

        Double foodTotalSpend = expensesUser.stream()
                .filter(e -> e.category.getIdCategory() == 1) // food category
                .filter(e -> {
                    try {
                        Date expenseDate = sdf.parse(e.expense.getDate());
                        Calendar expenseCal = Calendar.getInstance();
                        expenseCal.setTime(expenseDate);
                        int expenseMonth = expenseCal.get(Calendar.MONTH) + 1;
                        int expenseYear = expenseCal.get(Calendar.YEAR);

                        return (expenseMonth == currentMonth) && (expenseYear == currentYear);
                    } catch (Exception ex) {
                        Log.e("totalSpendError", ex.getMessage());
                        return false;
                    }
                })
                .mapToDouble(e -> e.expense.getAmount())
                .sum();






        Double educationTotalSpend = expensesUser.stream()
                .filter(e -> e.category.getIdCategory() == 2) // education category
                .filter(e -> {
                    try {
                        Date expenseDate = sdf.parse(e.expense.getDate());
                        Calendar expenseCal = Calendar.getInstance();
                        expenseCal.setTime(expenseDate);
                        int expenseMonth = expenseCal.get(Calendar.MONTH) + 1;
                        int expenseYear = expenseCal.get(Calendar.YEAR);

                        return (expenseMonth == currentMonth) && (expenseYear == currentYear);
                    } catch (Exception ex) {
                        Log.e("totalSpendError", ex.getMessage());
                        return false;
                    }
                })
                .mapToDouble(e -> e.expense.getAmount())
                .sum();





        Double InvoiceTotalSpend = expensesUser.stream()
                .filter(e -> e.category.getIdCategory() == 3) // invoice category
                .filter(e -> {
                    try {
                        Date expenseDate = sdf.parse(e.expense.getDate());
                        Calendar expenseCal = Calendar.getInstance();
                        expenseCal.setTime(expenseDate);
                        int expenseMonth = expenseCal.get(Calendar.MONTH) + 1;
                        int expenseYear = expenseCal.get(Calendar.YEAR);

                        return (expenseMonth == currentMonth) && (expenseYear == currentYear);
                    } catch (Exception ex) {
                        Log.e("totalSpendError", ex.getMessage());
                        return false;
                    }
                })
                .mapToDouble(e -> e.expense.getAmount())
                .sum();






        Double healthTotalSpend = expensesUser.stream()
                .filter(e -> e.category.getIdCategory() == 4) // health category
                .filter(e -> {
                    try {
                        Date expenseDate = sdf.parse(e.expense.getDate());
                        Calendar expenseCal = Calendar.getInstance();
                        expenseCal.setTime(expenseDate);
                        int expenseMonth = expenseCal.get(Calendar.MONTH) + 1;
                        int expenseYear = expenseCal.get(Calendar.YEAR);

                        return (expenseMonth == currentMonth) && (expenseYear == currentYear);
                    } catch (Exception ex) {
                        Log.e("totalSpendError", ex.getMessage());
                        return false;
                    }
                })
                .mapToDouble(e -> e.expense.getAmount())
                .sum();






        Double otherTotalSpend = expensesUser.stream()
                .filter(e -> e.category.getIdCategory() == 5) // other category
                .filter(e -> {
                    try {
                        Date expenseDate = sdf.parse(e.expense.getDate());
                        Calendar expenseCal = Calendar.getInstance();
                        expenseCal.setTime(expenseDate);
                        int expenseMonth = expenseCal.get(Calendar.MONTH) + 1;
                        int expenseYear = expenseCal.get(Calendar.YEAR);

                        return (expenseMonth == currentMonth) && (expenseYear == currentYear);
                    } catch (Exception ex) {
                        Log.e("totalSpendError", ex.getMessage());
                        return false;
                    }
                })
                .mapToDouble(e -> e.expense.getAmount())
                .sum();


        categoryTotalSpend.put("food",foodTotalSpend);
        categoryTotalSpend.put("education",educationTotalSpend);
        categoryTotalSpend.put("invoice",InvoiceTotalSpend);
        categoryTotalSpend.put("health",healthTotalSpend);
        categoryTotalSpend.put("other",otherTotalSpend);







        return categoryTotalSpend;



    }
}
