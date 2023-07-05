package com.hitzseb.walletwizardapi.repo;

import com.hitzseb.walletwizardapi.enums.OperationType;
import com.hitzseb.walletwizardapi.model.Category;
import com.hitzseb.walletwizardapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);

    @Query("SELECT c, SUM(o.amount) as totalAmount FROM Category c" +
            " JOIN c.operations o" +
            " WHERE o.type = :type" +
            " AND o.user = :user" +
            " GROUP BY c.id" +
            " ORDER BY totalAmount DESC")
    List<Object[]> findCategoriesByAmount(User user, OperationType type);
}
