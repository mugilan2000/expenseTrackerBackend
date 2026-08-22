package com.msm.expenseTrackerBackend.repo;

import com.msm.expenseTrackerBackend.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Integer> {
    List<Expense> findByUserId(Long userId);
}
