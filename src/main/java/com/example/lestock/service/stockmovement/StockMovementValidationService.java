package com.example.lestock.service.stockmovement;

import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.dao.stock.StockMovementDAO;
import com.example.lestock.exceptions.InvalidRecordException;
import com.example.lestock.exceptions.ResourceNotFoundException;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.MovementType;
import com.example.lestock.model.stock.Stock;
import com.example.lestock.model.stock.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockMovementValidationService {
    private final StockMovementDAO stockMovementDAO;

    public StockMovement getExistingStockMovement(Long stockMovementId) {
        return stockMovementDAO.findById(stockMovementId)
                .orElseThrow(() -> new ResourceNotFoundException("StockMovement not found with id " + stockMovementId));
    }

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

    public void validateStockMovementUpdate(StockMovement updatedMovement) {
        Stock stock = updatedMovement.getStock();

        float simulatedQuantity = stock.getStockMovements().stream()
                .filter(sm -> !sm.getId().equals(updatedMovement.getId()))
                .map(this::calculateImpact)
                .reduce(0f, Float::sum);

        simulatedQuantity += calculateImpact(updatedMovement);

        if (simulatedQuantity < 0) {
            FieldErrorDTO fieldError = new FieldErrorDTO(
                    "quantity",
                    "Quantity can't be greater than available stock",
                    "InvalidValue"
            );
            throw new InvalidRecordException("Invalid quantity", List.of(fieldError));
        }

        if (isUnitPriceInValid(updatedMovement)) {
            FieldErrorDTO fieldError = new FieldErrorDTO(
                    "unitPrice",
                    "Unit price is required for purchase operation",
                    "NotNull"
            );
            throw new InvalidRecordException("Invalid unitPrice", List.of(fieldError));
        }
    }

    private float calculateImpact(StockMovement sm) {
        return sm.getMovementType().equals(MovementType.PURCHASE)
                ? sm.getQuantity()
                : -sm.getQuantity();
    }

    public void validateStockMovementFromMaterialType(MaterialType materialType, StockMovement stockMovement) {
        if(materialType.getStock() == null || !stockMovement.getStock().getMaterialType().equals(materialType)) {
            FieldErrorDTO fieldError = new FieldErrorDTO("materialTypeId",
                    "This stock movement doesn't belong to this material type",
                    "invalidMaterialType");
            throw new InvalidRecordException("Invalid materialType", List.of(fieldError));
        }
    }

}
