package com.hitzseb.billeterapp.repository;

import com.hitzseb.billeterapp.model.Category;
import com.hitzseb.billeterapp.model.Operation;
import com.hitzseb.billeterapp.model.Transaction;
import com.hitzseb.billeterapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findAllByUser(User user);

    @Query("SELECT o FROM Operation o WHERE o.user = :user"
            + " AND (:transaction IS NULL OR o.transaction = :transaction)"
            + " AND (:category IS NULL OR o.category = :category)"
            + " AND (:date IS NULL OR o.date >= :date)")
    List<Operation> findAllByTypeAndCategoryAndDateAndOrder(
            @Param("user") User user,
            @Param("transaction") Transaction transaction,
            @Param("category") Category category,
            @Param("date") LocalDate date);

    @Query("SELECT YEAR(o.date) as year, MONTH(o.date) as month, SUM(o.amount) as total FROM Operation o " +
            "WHERE o.user = :user AND o.transaction = :transaction " +
            "GROUP BY year, month " +
            "ORDER BY year, month")
    List<Object[]> findMonthsByAmount(User user, Transaction transaction);

}
