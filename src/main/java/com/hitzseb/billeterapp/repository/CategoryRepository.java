package com.hitzseb.billeterapp.repository;

import com.hitzseb.billeterapp.model.Category;
import com.hitzseb.billeterapp.model.Transaction;
import com.hitzseb.billeterapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUser(User user);

    @Query("SELECT c, SUM(o.amount) as totalAmount FROM Category c" +
            " JOIN c.operations o" +
            " WHERE o.transaction = :transaction" +
            " AND o.user = :user" +
            " GROUP BY c.id" +
            " ORDER BY totalAmount DESC")
    List<Object[]> findCategoriesByAmount(User user, Transaction transaction);
}
