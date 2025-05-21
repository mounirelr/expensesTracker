package com.example.expensestracker;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.expensestracker.R;
import com.example.expensestracker.model.ExpenseWithWalletAndCategory;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder> {

    private List<ExpenseWithWalletAndCategory> expenseList;

    public ExpenseAdapter(List<ExpenseWithWalletAndCategory> expenseList) {
        this.expenseList = expenseList;
    }

    @Override
    public ExpenseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the item layout (your expense_item_layout.xml)
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_expense, parent, false);
        return new ExpenseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpenseViewHolder holder, int position) {
        // Get the current expense object
        ExpenseWithWalletAndCategory expenseWithWalletAndCategory = expenseList.get(position);

        // Set the expense details in the views
        holder.titleTextView.setText(expenseWithWalletAndCategory.expense.getTitle());
        holder.descriptionTextView.setText(expenseWithWalletAndCategory.expense.getDescription());
        holder.categoryTextView.setText(expenseWithWalletAndCategory.category.getCategoryName());
        holder.dateTextView.setText(expenseWithWalletAndCategory.expense.getDate());
        holder.amountTextView.setText(String.format("%.2f %s", expenseWithWalletAndCategory.expense.getAmount(),expenseWithWalletAndCategory.wallet.getCurrency()));
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    // ViewHolder class for each expense item
    public static class ExpenseViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, descriptionTextView, categoryTextView, dateTextView, amountTextView;

        public ExpenseViewHolder(View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.textExpenseTitle);
            descriptionTextView = itemView.findViewById(R.id.textExpenseDescription);
            categoryTextView = itemView.findViewById(R.id.textExpenseCategory);
            dateTextView = itemView.findViewById(R.id.textExpenseDate);
            amountTextView = itemView.findViewById(R.id.textExpenseAmount);
        }
    }
}
