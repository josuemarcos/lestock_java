package com.example.lestock.service.stock;
import com.example.lestock.dao.stock.StockDAO;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.Stock;
import com.example.lestock.model.User;
import com.example.lestock.service.materialtype.MaterialTypeValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockDAO stockDAO;
    private final StockValidationService stockValidationService;
    private final MaterialTypeValidationService materialTypeValidationService;

    @Transactional
    public Stock saveStock(Long materialTypeId, Stock stock, User loggedUser) {
        MaterialType materialType = materialTypeValidationService.getExistingMaterialType(materialTypeId);
        stockValidationService.validateStock(stock);
        stock.setUserId(loggedUser.getId());
        materialType.setStock(stock);
        return stock;
    }
    public List<Stock> getAllStocks() {
        return stockDAO.findAll();
    }

    public Stock getStockByMaterialType(Long materialTypeId) {
        MaterialType materialType = materialTypeValidationService.getExistingMaterialType(materialTypeId);
        return stockValidationService.getStockByMaterialType(materialType);
    }
}
