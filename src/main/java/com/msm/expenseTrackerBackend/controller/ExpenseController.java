package com.msm.expenseTrackerBackend.controller;

import com.msm.expenseTrackerBackend.model.Expense;
import com.msm.expenseTrackerBackend.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public ResponseEntity<Object> getAllExpenses() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Object> getExpenseById(@PathVariable int id) {
        return expenseService.getExpenseById(id);
    }

    @PostMapping("/expenses")
    public ResponseEntity<Object> createExpense(@RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    @DeleteMapping("/expenses/{id}")
    public ResponseEntity<Object> deleteExpense(@PathVariable int id) {
        return expenseService.deleteExpense(id);
    }
}
