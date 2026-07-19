package com.msm.expenseTrackerBackend.service;

import com.msm.expenseTrackerBackend.model.Expense;
import com.msm.expenseTrackerBackend.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepo;

    public ResponseEntity<Object> getAllExpenses() {
        return ResponseEntity.ok(expenseRepo.findAll());
    }

    public ResponseEntity<Object> getExpenseById(int id) {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent()) {
            return ResponseEntity.ok(expense.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> saveExpense(Expense expense) {
        expense.setId(getLastId() + 1);
        expenseRepo.save(expense);
        return ResponseEntity.ok(expense);
    }

    public ResponseEntity<Object> deleteExpense(int id) {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent()) {
            expenseRepo.deleteById(id);
            return ResponseEntity.ok(expense.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public int getLastId() {
        List<Expense> expenses = expenseRepo.findAll();
        if (!expenses.isEmpty()) {
            Expense lastExpense = expenses.get(expenses.size() - 1);
            int lastId = lastExpense.getId();
            return lastId;
        } else {
            return 0;
        }
    }
}
