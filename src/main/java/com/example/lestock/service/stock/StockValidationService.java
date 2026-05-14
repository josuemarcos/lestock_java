package com.example.lestock.service.stock;

import com.example.lestock.controller.dto.errors.FieldErrorDTO;
import com.example.lestock.dao.stock.StockDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.exceptions.ResourceNotFoundException;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockValidationService {
    private final StockDAO stockDAO;

    public void validateStock(Stock stock) {
        if(isStockSaved(stock)) {
            FieldErrorDTO fieldErrorDTO = new FieldErrorDTO(
                    "materialType",
                    "Stock already exists for this material type",
                    "UniquenessBreak");
            throw new DuplicateRecordException("Invalid stock", List.of(fieldErrorDTO));
        }
    }

    private Boolean isStockSaved(Stock stock) {
        Optional<Stock> stockOptional = stockDAO.findByMaterialType(stock.getMaterialType());

        if (stock.getId() == null) {
            return stockOptional.isPresent();
        }
        return stockOptional
                .map(Stock::getId)
                .stream()
                .anyMatch(
                        id -> !id.equals(stock.getId())
                );
    }

    public Stock getStockByMaterialType(MaterialType materialType) {
        if(materialType.getStock() == null) {
            throw new ResourceNotFoundException("Stock of material" + materialType.getName() + " not found");
        }
        return materialType.getStock();
    }
}
