package com.hitzseb.wallet.service;

import com.hitzseb.wallet.model.Category;
import com.hitzseb.wallet.model.User;
import com.hitzseb.wallet.repo.CategoryRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepo categoryRepo;
    public List<Category> getAllCategories() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return categoryRepo.findByUser(user);
    }
    public Category saveCategory(Optional<String> name) throws IllegalArgumentException {
        if (!name.isPresent()) {
            throw new IllegalArgumentException("Parameter name is empty");
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Category category = new Category();
        category.setName(name.get());
        category.setUser(user);
        return categoryRepo.save(category);
    }

    public Category updateCategory(Long id, Optional<String> name)
            throws EntityNotFoundException {
        Category category = categoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        if (name.isPresent()) {
            category.setName(name.get());
        }
        return categoryRepo.save(category);
    }


    public void deleteCategoryById(Long id) throws EntityNotFoundException, ServiceException {
        Optional<Category> category = categoryRepo.findById(id);
        if (!category.isPresent()) {
            throw new EntityNotFoundException("Category not found with id: " + id);
        }
        try {
            categoryRepo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete category with id: " + id, e);
        }
    }

    public boolean categoryBelongsToUser(Long userId, Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElse(null);
        return category != null && category.getUser().getId().equals(userId);
    }

}
