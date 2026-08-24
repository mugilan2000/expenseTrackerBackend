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

    public ResponseEntity<Object> getAllExpenses(Long currentUserId) {
        List<Expense> expenses = expenseRepo.findByUserId(currentUserId);
        return ResponseEntity.ok(expenses);
    }

    public ResponseEntity<Object> getExpensesByUserId(Long userId, Long currentUserId) {
        if (!userId.equals(currentUserId)) {
            return ResponseEntity.status(403).body("Forbidden: You can only access your own expenses");
        }
        List<Expense> expenses = expenseRepo.findByUserId(userId);
        return ResponseEntity.ok(expenses);
    }

    public ResponseEntity<Object> getExpenseById(int id, Long currentUserId) {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent()) {
            if (!expense.get().getUserId().equals(currentUserId)) {
                return ResponseEntity.status(403).body("Forbidden: This expense belongs to another user");
            }
            return ResponseEntity.ok(expense.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Object> saveExpense(Expense expense, Long currentUserId) {
        expense.setUserId(currentUserId);
        expense.setId(getLastId() + 1);
        expenseRepo.save(expense);
        return ResponseEntity.ok(expense);
    }

    public ResponseEntity<Object> deleteExpense(int id, Long currentUserId) {
        Optional<Expense> expense = expenseRepo.findById(id);
        if (expense.isPresent()) {
            if (!expense.get().getUserId().equals(currentUserId)) {
                return ResponseEntity.status(403).body("Forbidden: This expense belongs to another user");
            }
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
