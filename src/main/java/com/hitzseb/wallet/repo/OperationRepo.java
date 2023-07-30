package com.hitzseb.wallet.repo;

import com.hitzseb.wallet.enums.OperationType;
import com.hitzseb.wallet.model.Category;
import com.hitzseb.wallet.model.Operation;
import com.hitzseb.wallet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OperationRepo extends JpaRepository<Operation, Long> {

    List<Operation> findAllByUser(User user);

    @Query("SELECT o FROM Operation o WHERE o.user = :user"
            + " AND (:type IS NULL OR o.type = :type)"
            + " AND (:category IS NULL OR o.category = :category)"
            + " AND (:date IS NULL OR o.date >= :date)")
    List<Operation> findAllByTypeAndCategoryAndDateAndOrder(
            @Param("user") User user,
            @Param("type") OperationType type,
            @Param("category") Category category,
            @Param("date") LocalDate date);

    @Query("SELECT YEAR(o.date) as year, MONTH(o.date) as month, SUM(o.amount) as total FROM Operation o " +
            "WHERE o.user = :user AND o.type = :type " +
            "GROUP BY year, month " +
            "ORDER BY year, month")
    List<Object[]> findMonthsByAmount(User user, OperationType type);

}

