package com.hitzseb.wallet.controller;

import com.hitzseb.wallet.model.Category;
import com.hitzseb.wallet.responses.CategoryResponse;
import com.hitzseb.wallet.responses.DeleteResponse;
import com.hitzseb.wallet.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/category")
@CrossOrigin("https://hitzseb-wallet-wizard.web.app/")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Retrieves categories for a logged-in user.",
            description = "Returns all the categories related with the logged-in user.")
    @GetMapping("/all")
    @PreAuthorize("@categoryService.categoryBelongsToUser(authentication.principal.id, #id)")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @Operation(summary = "Creates a new category.",
            description = "Creates a new category with the specified details." +
                    " The `name` parameter is used to specify the category details." +
                    " Returns a response with a message indicating" +
                    " that the category was created successfully" +
                    " and the category details in the response body.")
    @PostMapping("/new")
    @PreAuthorize("@categoryService.categoryBelongsToUser(authentication.principal.id, #id)")
    public ResponseEntity<?> createCategory(@RequestParam Optional<String> name) {
        try {
            Category category = categoryService.saveCategory(name);
            CategoryResponse response = new CategoryResponse("Category created successfully", category);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @Operation(summary = "Updates an existing category.",
            description = "Updates an existing category with the specified ID and details." +
                    " The `id` path variable is used to identify the category to update," +
                    " and the `name` parameter is used to specify the updated category details." +
                    " Returns a response with a message indicating that the category" +
                    " was updated successfully and the updated category details in the response body." +
                    " The authenticated user must have the same ID as the category being updated.")
    @PutMapping("/{id}/edit")
    @PreAuthorize("@categoryService.categoryBelongsToUser(authentication.principal.id, #id)")
    public ResponseEntity<?> editCategory(@PathVariable Long id,
                                          @RequestParam Optional<String> name) {
        try {
            Category category = categoryService.updateCategory(id, name);
            CategoryResponse response = new CategoryResponse("Category updated successfully", category);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @Operation(summary = "Deletes an existing category.",
            description = "Deletes an existing category with the specified ID." +
                    " Returns a response with a message indicating that the category" +
                    " was deleted successfully and the category's `id`.")
    @DeleteMapping("/{id}/delete")
    @PreAuthorize("@categoryService.categoryBelongsToUser(authentication.principal.id, #id)")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            DeleteResponse response = new DeleteResponse("Category deleted successfully", id);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ServiceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
