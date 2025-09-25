package com.example.lestock.service;

import com.example.lestock.dao.StockMovementDAO;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Stock;
import com.example.lestock.model.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementDAO stockMovementDAO;
    private final StockService stockService;


    public Optional<StockMovement> getStockMovement(Long id) {
        return stockMovementDAO.findById(id);
    }
    public void save(StockMovement stockMovement) {
        stockMovementDAO.save(stockMovement);
    }
    public List<StockMovement> getAllStockMovements() {
        return stockMovementDAO.findAll();
    }

    public void updateStock(StockMovement stockMovement, MaterialType materialType) {
        String operation = stockMovement.getMovementType().toString();
        switch (operation) {
            case "PURCHASE" -> incrementStock(stockMovement,  materialType);
            case "SALE" -> decrementStock(stockMovement,  materialType);
            case "ADJUSTMENT" -> undoStockMovement(stockMovement, materialType);
        }
    }

    private void incrementStock(StockMovement stockMovement, MaterialType  materialType) {
        stockService.getStockByMaterialType(materialType)
                .map( stock -> {
                    float oldTotalCost = stock.getCurrentQuantity() * stock.getAverageCost();
                    float newTotalCost = stockMovement.getQuantity() * stockMovement.getUnitPrice();
                    stock.setAverageCost((oldTotalCost + newTotalCost) / (stock.getCurrentQuantity() + stockMovement.getQuantity()));
                    stock.setCurrentQuantity(stock.getCurrentQuantity() + stockMovement.getQuantity());
                    stockService.updateStock(stock);
                    return null;
                });
    }
    private void decrementStock(StockMovement stockMovement, MaterialType  materialType) {
        stockService.getStockByMaterialType(materialType)
                .map(stock -> {
                    stock.setCurrentQuantity(stock.getCurrentQuantity() - stockMovement.getQuantity());
                    stockService.updateStock(stock);
                    return null;
                });
    }

    private void undoStockMovement(StockMovement stockMovement,  MaterialType  materialType) {
        stockService.getStockByMaterialType(materialType)
                .map(stock -> {
                    float oldQuantity = stock.getCurrentQuantity() - stockMovement.getQuantity();
                    float oldTotalCost = stock.getAverageCost()*stock.getCurrentQuantity();
                    float newTotalCost = stockMovement.getUnitPrice()*stockMovement.getQuantity();
                    float oldAverageCost = (oldTotalCost - newTotalCost)/oldQuantity;
                    stock.setCurrentQuantity(oldQuantity);
                    stock.setAverageCost(oldAverageCost);
                    stockService.updateStock(stock);
                    return null;
                });
    }
}
