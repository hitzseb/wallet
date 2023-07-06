package com.hitzseb.wallet.service;

import com.hitzseb.wallet.dto.OperationRequest;
import com.hitzseb.wallet.enums.OperationType;
import com.hitzseb.wallet.model.Category;
import com.hitzseb.wallet.model.Operation;
import com.hitzseb.wallet.model.User;
import com.hitzseb.wallet.repo.CategoryRepo;
import com.hitzseb.wallet.repo.OperationRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepo operationRepo;
    private final CategoryRepo categoryRepo;
    private final UserService userService;

    public List<Operation> getAllOperations(Optional<OperationType> type,
                                            Optional<Long> categoryId,
                                            Optional<LocalDate> date) throws EntityNotFoundException {
        Category category = null;
        if (categoryId.isPresent()) {
            category = categoryRepo.findById(categoryId.get())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        }
        User user = userService.getCurrentUser();
        return operationRepo.findAllByTypeAndCategoryAndDateAndOrder(
                user, type.orElse(null), category, date.orElse(null));
    }

    public Operation saveOperation(Optional<OperationRequest> request) throws IllegalArgumentException, EntityNotFoundException {
        OperationRequest operationRequest = request.orElseThrow(() -> new IllegalArgumentException("Operation request is empty"));

        String description = operationRequest.getDescription().orElseThrow(() -> new IllegalArgumentException("Parameter description is empty"));
        Double amount = operationRequest.getAmount().orElseThrow(() -> new IllegalArgumentException("Parameter amount is empty"));
        OperationType type = operationRequest.getType().orElseThrow(() -> new IllegalArgumentException("Parameter type is empty"));
        Long categoryId = operationRequest.getCategoryId().orElseThrow(() -> new IllegalArgumentException("Parameter categoryId is empty"));
        LocalDate date = operationRequest.getDate().orElseThrow(() -> new IllegalArgumentException("Parameter date is empty"));

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + categoryId));
        User user = userService.getCurrentUser();

        Operation operation = new Operation();
        operation.setDescription(description);
        operation.setAmount(amount);
        operation.setType(type);
        operation.setCategory(category);
        operation.setDate(date);
        operation.setUser(user);

        return operationRepo.save(operation);
    }

    public Operation updateOperation(Long id,
                                Optional<String> description,
                                Optional<Double> amount,
                                Optional<OperationType> type,
                                Optional<Long> categoryId,
                                Optional<LocalDate> date) throws EntityNotFoundException {
        Operation operation = operationRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Operation not found with id: " + id));
        if (description.isPresent()) {
            operation.setDescription(description.get());
        }
        if (amount.isPresent()) {
            operation.setAmount(amount.get());
        }
        if (type.isPresent()) {
            operation.setType(type.get());
        }
        if (categoryId.isPresent()) {
            Category category = categoryRepo.findById(categoryId.get())
                    .orElseThrow(() -> new EntityNotFoundException(
                            "Category not found with id: " + categoryId));
            operation.setCategory(category);
        }
        if (date.isPresent()) {
            operation.setDate(date.get());
        }
        return operationRepo.save(operation);
    }

    public void deleteOperationById(Long id) throws EntityNotFoundException, ServiceException {
        Optional<Operation> operation = operationRepo.findById(id);
        if (!operation.isPresent()) {
            throw new EntityNotFoundException("Operation not found with id: " + id);
        }
        try {
            operationRepo.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Failed to delete operation with id: " + id, e);
        }
    }

    public boolean operationBelongsToUser(Long userId, Long operationId) {
        Operation operation = operationRepo.findById(operationId).orElse(null);
        return operation != null && operation.getUser().getId().equals(userId);
    }

}
