package com.msm.expenseTrackerBackend.controller;

import com.msm.expenseTrackerBackend.model.Expense;
import com.msm.expenseTrackerBackend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    private Long getCurrentUserId(HttpServletRequest request) {
        return (Long) request.getAttribute("userId");
    }

    @GetMapping("/expenses")
    public ResponseEntity<Object> getAllExpenses(HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return expenseService.getAllExpenses(currentUserId);
    }

    @GetMapping("/expenses/user/{userId}")
    public ResponseEntity<Object> getExpensesByUserId(@PathVariable Long userId, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return expenseService.getExpensesByUserId(userId, currentUserId);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Object> getExpenseById(@PathVariable int id, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return expenseService.getExpenseById(id, currentUserId);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Object> createExpense(@RequestBody Expense expense, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return expenseService.saveExpense(expense, currentUserId);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Object> deleteExpense(@PathVariable int id, HttpServletRequest request) {
        Long currentUserId = getCurrentUserId(request);
        if (currentUserId == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        return expenseService.deleteExpense(id, currentUserId);
    }
}
