package com.example.lestock.service;

import com.example.lestock.dao.StockMovementDAO;
import com.example.lestock.model.Stock;
import com.example.lestock.model.StockMovement;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void updateStock(StockMovement stockMovement) {
        String operation = stockMovement.getMovementType().toString();
        switch (operation) {
            case "PURCHASE" -> incrementStock(stockMovement);
            case "SALE" -> decrementStock(stockMovement);
            case "ADJUSTMENT" -> {
                undoStockMovement(stockMovement);
                save(stockMovement);
            }
        }
    }

    private void incrementStock(StockMovement stockMovement) {
        Stock stock = stockMovement.getStock();
        float oldTotalCost = stock.getCurrentQuantity() * stock.getAverageCost();
        float newTotalCost = stockMovement.getQuantity() * stockMovement.getUnitPrice();
        stock.setAverageCost((oldTotalCost + newTotalCost) / (stock.getCurrentQuantity() + stockMovement.getQuantity()));
        stock.setCurrentQuantity(stock.getCurrentQuantity() + stockMovement.getQuantity());
    }
    private void decrementStock(StockMovement stockMovement) {
        Stock stock = stockMovement.getStock();
        stock.setCurrentQuantity(stock.getCurrentQuantity() - stockMovement.getQuantity());
    }

    private void undoStockMovement(StockMovement stockMovement) {
        Stock stock = stockService.getStockById(stockMovement.getStock().getId()).orElse(null);
        if(stock != null) {
            float oldQuantity = stock.getCurrentQuantity() - stockMovement.getQuantity();
            float oldAverageCost = (stock.getAverageCost()*stock.getCurrentQuantity() - stockMovement.getQuantity()*stockMovement.getUnitPrice())/oldQuantity;
            stock.setCurrentQuantity(oldAverageCost);
            stock.setAverageCost(oldAverageCost);
            stockService.updateStock(stock);
        }
    }
}
