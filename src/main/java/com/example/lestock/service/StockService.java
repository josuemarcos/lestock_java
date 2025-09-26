package com.example.lestock.service;
import com.example.lestock.dao.StockDAO;
import com.example.lestock.model.Material;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Stock;
import com.example.lestock.validator.StockValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockDAO stockDAO;
    private final StockValidator stockValidator;

    public void saveStock(Stock stock) {
        stockValidator.validateStock(stock);
        stockDAO.save(stock);
    }
    public List<Stock> getAllStocks() {
        return stockDAO.findAll();
    }

    public Optional<Stock> getStockByMaterialType(MaterialType materialType) {
        return stockDAO.findByMaterialType(materialType);
    }

    public void updateStock(Stock stock) {
        stockValidator.validateStock(stock);
        stockDAO.save(stock);
    }

    public Optional<Stock> getStockById(Long id) {
        return stockDAO.findById(id);
    }
}
