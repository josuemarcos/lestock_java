package com.example.lestock.validator;
import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.exceptions.InvalidRecordException;
import com.example.lestock.model.MovementType;
import com.example.lestock.model.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockMovementValidator  {

    public void validateStockMovement(StockMovement stockMovement) {
        if(isQuantityInvalid(stockMovement)) {
            FieldErrorDTO fieldError = new FieldErrorDTO(
                    "quantity",
                    "Quantity can't be greater than available stock",
                    "InvalidValue"
            );
            throw new InvalidRecordException("Invalid quantity", List.of(fieldError));
        }
        if(isUnitPriceInValid(stockMovement)) {
            FieldErrorDTO fieldError = new FieldErrorDTO(
                    "unitPrice",
                    "Unit price is required for purchase operation",
                    "NotNull"
            );
            throw new InvalidRecordException("Invalid unitPrice", List.of(fieldError));
        }
    }

    private boolean isQuantityInvalid(StockMovement stockMovement) {
        return stockMovement.getMovementType().equals(MovementType.SALE)
                && stockMovement.getQuantity() > stockMovement.getStock().getCurrentQuantity();
    }
    private boolean isUnitPriceInValid(StockMovement stockMovement) {
        return stockMovement.getMovementType().equals(MovementType.PURCHASE) && stockMovement.getUnitPrice() == null;
    }






}
