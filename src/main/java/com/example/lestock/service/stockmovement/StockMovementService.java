package com.example.lestock.service.stockmovement;
import com.example.lestock.controller.dto.stock.SaveStockMovementDTO;
import com.example.lestock.controller.mapper.stock.StockMovementMapper;
import com.example.lestock.dao.stock.StockMovementDAO;
import com.example.lestock.model.*;
import com.example.lestock.model.stock.MaterialType;
import com.example.lestock.model.stock.Stock;
import com.example.lestock.model.stock.StockMovement;
import com.example.lestock.model.stock.Supplier;
import com.example.lestock.service.materialtype.MaterialTypeValidationService;
import com.example.lestock.service.stock.StockValidationService;
import com.example.lestock.service.supplier.SupplierValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementDAO stockMovementDAO;
    private final MaterialTypeValidationService materialTypeValidationService;
    private final StockValidationService stockValidationService;
    private final StockMovementValidationService stockMovementValidationService;
    private final StockMovementMapper stockMovementMapper;
    private final SupplierValidationService supplierValidationService;



    public Optional<StockMovement> getStockMovement(Long id) {
        return stockMovementDAO.findById(id);
    }

    @Transactional
    public StockMovement save(Long materialTypeId, SaveStockMovementDTO stockMovementDTO, User loggedUser) {
        MaterialType materialType = materialTypeValidationService.getExistingMaterialType(materialTypeId);
        Supplier supplier = resolveSupplier(stockMovementDTO.supplierId());
        if(materialType.getStock() == null) {
            Stock stock = Stock.builder().build();
            materialType.setStock(stock);
        }
        StockMovement stockMovement = stockMovementMapper.toEntity(stockMovementDTO);
        materialType.getStock().addStockMovement(stockMovement);
        stockMovement.setUserId(loggedUser.getId());
        stockMovement.setSupplier(supplier);
        stockMovementValidationService.validateStockMovement(stockMovement);
        return stockMovementDAO.save(stockMovement);
    }

    public List<StockMovement> getAllStockMovements() {
        return stockMovementDAO.findAll();
    }

    public List<StockMovement> getAllStockMovementsByMaterialType(Long materialTypeId) {
        MaterialType materialType = materialTypeValidationService.getExistingMaterialType(materialTypeId);
        Stock stock = stockValidationService.getStockByMaterialType(materialType);
        return stockMovementDAO.findByStock(stock);
    }

    @Transactional
    public void updateStockMovement(Long materialTypeId, Long stockMovementId, SaveStockMovementDTO updatedStockMovement, User loggedUser) {
        MaterialType materialType = materialTypeValidationService.getExistingMaterialType(materialTypeId);
        Supplier supplier = resolveSupplier(updatedStockMovement.supplierId());
        StockMovement stockMovement = stockMovementValidationService.getExistingStockMovement(stockMovementId);
        stockMovementValidationService.validateStockMovementFromMaterialType(materialType, stockMovement);
        stockMovementMapper.updateEntity(stockMovement, updatedStockMovement);
        stockMovement.setStock(materialType.getStock());
        stockMovement.setSupplier(supplier);
        stockMovement.setUserId(loggedUser.getId());
        stockMovementValidationService.validateStockMovementUpdate(stockMovement);
        stockMovementDAO.save(stockMovement);
    }

    @Transactional
    public void undoStockMovement(Long materialTypeId, Long stockMovementId, SaveStockMovementDTO updatedStockMovement) {
        MaterialType materialType = materialTypeValidationService.getExistingMaterialType(materialTypeId);
        Stock stock = stockValidationService.getStockByMaterialType(materialType);
        StockMovement stockMovement = stockMovementValidationService.getExistingStockMovement(stockMovementId);
    }

    private Supplier resolveSupplier(Long supplierId) {
        if (supplierId == null) {
            return null;
        }

        return supplierValidationService
                .getExistingSupplier(supplierId);
    }
}
