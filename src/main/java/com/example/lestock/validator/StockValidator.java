package com.example.lestock.validator;
import com.example.lestock.controller.dto.SaveStockDTO;
import com.example.lestock.dao.StockDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.Stock;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockValidator {
    private final StockDAO stockDAO;

    public void validateStock(Stock stock) {
        if(isStockSaved(stock)) {
            throw new DuplicateRecordException("Stock already exists");
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


}
