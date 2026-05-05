package com.example.lestock.service.stock;
import com.example.lestock.dao.stock.StockMovementDAO;
import com.example.lestock.model.*;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.MovementType;
import com.example.lestock.model.stock.Stock;
import com.example.lestock.model.stock.StockMovement;
import com.example.lestock.security.SecurityService;
import com.example.lestock.validator.stock.StockMovementValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementDAO stockMovementDAO;
    private final StockService stockService;
    private final StockMovementValidator stockMovementValidator;
    private final SecurityService securityService;


    public Optional<StockMovement> getStockMovement(Long id) {
        return stockMovementDAO.findById(id);
    }
    public void save(StockMovement stockMovement, MaterialType materialType, User loggedUser) {
        stockMovementValidator.validateStockMovement(stockMovement);
        stockMovement.setUserId(loggedUser.getId());
        materialType.getStock().addStockMovement(stockMovement);
    }
    public List<StockMovement> getAllStockMovements() {
        return stockMovementDAO.findAll();
    }
    public List<StockMovement> getAllStockMovementsByStock(Stock stock) {
        return stockMovementDAO.findByStock(stock);
    }

    public void updateStock(StockMovement stockMovement, User loggedUser) {
        stockMovementValidator.validateStockMovementUpdate(stockMovement);
        stockMovement.setUserId(loggedUser.getId());
        stockMovementDAO.save(stockMovement);
    }

    public void undoStockMovement(StockMovement stockMovement, MaterialType  materialType) {
        Stock  stock = stockMovement.getStock();
        stock.removeStockMovement(stockMovement);

    }
}
