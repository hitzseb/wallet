package com.hitzseb.billeterapp.service;

import com.hitzseb.billeterapp.dto.OperationRequest;
import com.hitzseb.billeterapp.model.Category;
import com.hitzseb.billeterapp.model.Operation;
import com.hitzseb.billeterapp.model.Transaction;
import com.hitzseb.billeterapp.model.User;
import com.hitzseb.billeterapp.repository.CategoryRepository;
import com.hitzseb.billeterapp.repository.OperationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final CategoryRepository categoryRepository;

    public List<Operation> getAllOperations(Optional<Transaction> transaction,
                                            Optional<Long> categoryId,
                                            Optional<LocalDate> date) throws EntityNotFoundException {
        Category category = null;
        if (categoryId.isPresent()) {
            category = categoryRepository.findById(categoryId.get())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        }
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return operationRepository.findAllByTypeAndCategoryAndDateAndOrder(
                user, transaction.orElse(null), category, date.orElse(null));
    }

    public Operation saveOperation(Optional<OperationRequest> request) throws IllegalArgumentException, EntityNotFoundException {
        OperationRequest operationRequest = request.orElseThrow(() -> new IllegalArgumentException("Operation request is empty"));

        String description = operationRequest.getDescription().orElseThrow(() -> new IllegalArgumentException("Parameter description is empty"));
        Double amount = operationRequest.getAmount().orElseThrow(() -> new IllegalArgumentException("Parameter amount is empty"));
        Transaction transaction = operationRequest.getTransaction().orElseThrow(() -> new IllegalArgumentException("Parameter type is empty"));
        Long categoryId = operationRequest.getCategoryId().orElseThrow(() -> new IllegalArgumentException("Parameter categoryId is empty"));
        LocalDate date = operationRequest.getDate().orElseThrow(() -> new IllegalArgumentException("Parameter date is empty"));

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Operation operation = new Operation();
        operation.setDescription(description);
        operation.setAmount(amount);
        operation.setTransaction(transaction);
        operation.setCategory(category);
        operation.setDate(date);
        operation.setUser(user);

        return operationRepository.save(operation);
    }

    public Operation updateOperation(Long id,
                                     Optional<String> description,
                                     Optional<Double> amount,
                                     Optional<Transaction> transaction,
                                     Optional<Long> categoryId,
                                     Optional<LocalDate> date) throws EntityNotFoundException {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Operation not found with id: " + id));
        if (description.isPresent()) {
            operation.setDescription(description.get());
        }
        if (amount.isPresent()) {
            operation.setAmount(amount.get());
        }
        if (transaction.isPresent()) {
            operation.setTransaction(transaction.get());
        }
        if (categoryId.isPresent()) {
            Category category = categoryRepository.findById(categoryId.get())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category not found with id: " + categoryId));
            operation.setCategory(category);
        }
        if (date.isPresent()) {
            operation.setDate(date.get());
        }
        return operationRepository.save(operation);
    }

    public void deleteOperationById(Long id) throws EntityNotFoundException, ServiceException {
        Optional<Operation> operation = operationRepository.findById(id);
        if (!operation.isPresent()) {
            throw new EntityNotFoundException("Operation not found with id: " + id);
        }
        try {
            operationRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete operation with id: " + id, e);
        }
    }

    public boolean operationBelongsToUser(Long userId, Long operationId) {
        Operation operation = operationRepository.findById(operationId).orElse(null);
        return operation != null && operation.getUser().getId().equals(userId);
    }

}
