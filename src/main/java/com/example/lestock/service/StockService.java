package com.example.lestock.service;
import com.example.lestock.dao.StockDAO;
import com.example.lestock.model.Material;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockDAO stockDAO;

    public void saveStock(Stock stock) {
        stockDAO.save(stock);
    }
    public List<Stock> getAllStocks() {
        return stockDAO.findAll();
    }

    public Optional<Stock> getStockByMaterialType(MaterialType materialType) {
        return stockDAO.findByMaterialType(materialType);
    }
}
