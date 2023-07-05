package com.hitzseb.walletwizardapi.responses;

import com.hitzseb.walletwizardapi.model.Operation;

public record OperationResponse(String message, Operation operation) {
}
