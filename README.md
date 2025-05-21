# 📱 Personal Expense Tracker App

A **simple and intuitive mobile application** for managing personal finances. Track your daily expenses, categorize them, and set budgets to keep your spending under control. This app supports user authentication via Volley API calls and stores data locally using Room Database.

---

## ✨ Features

- ➕ **Add Expenses**  
  Record daily expenses with amount, description, date, and category.

- 📜 **History of Expenses**  
  View a complete log of all your past expenses in a structured list.

- 🏷️ **Categorize Expenses**  
  Assign categories like Food, Travel, Entertainment, etc., to your expenses.

- 🎯 **Budget Specification**  
  Set monthly budgets to track and control your spending habits.

- 🔐 **User Authentication**  
  Login and registration using Volley API requests connected to a PHP + MySQL backend.

---

## 🛠️ Tech Stack

- **Platform**: Android (Java/Kotlin)
- **Backend**: PHP + MySQL
- **Networking**: Volley (API calls)
- **Database**: Room (SQLite ORM)
- **Architecture**: MVVM

---

## 🗃️ Project Structure

<pre>
/model
│   ├── Expense.java               # Data model for individual expense
│   ├── Category.java              # Data model for categories
│   └── Wallet.java                # Optional: Model for wallet/balance

/viewmodel
│   └── ExpenseWithWalletAndCategory.kt   # Combines expense with wallet and category data

/service
│   ├── AuthServiceVolley.java     # Handles login and registration API requests
│   ├── ExpenseService.java        # Interface for expense features
│   ├── CategoryService.java       # Interface for category features
│   └── BudgetService.java         # Interface for budget features



---

## 🚀 Getting Started

### Prerequisites

- Android Studio
- MySQL & PHP Server (e.g., XAMPP or live server)
- Internet connection (for API calls)

### Installation Steps

1. Clone the repository:

```bash
git clone https://github.com/your-username/expense-tracker-app.git
