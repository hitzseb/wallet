package com.hitzseb.wallet.repo;

import com.hitzseb.wallet.enums.OperationType;
import com.hitzseb.wallet.model.Category;
import com.hitzseb.wallet.model.User;
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
