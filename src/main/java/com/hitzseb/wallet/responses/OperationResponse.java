package com.hitzseb.wallet.responses;

import com.hitzseb.wallet.model.Operation;

public record OperationResponse(String message, Operation operation) {
}
