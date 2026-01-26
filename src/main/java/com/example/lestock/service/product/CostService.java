package com.example.lestock.service.product;
import com.example.lestock.dao.product.CostDAO;
import com.example.lestock.model.product.Cost;
import com.example.lestock.validator.product.CostValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CostService {

    private final CostDAO costDAO;
    private final CostValidator costValidator;

    public void saveCost(Cost cost) {
        costValidator.validateCost(cost);
        costDAO.save(cost);
    }

    public List<Cost> getAllCosts() {
        return costDAO.findAll();
    }

    public Optional<Cost> getCostById(Long id) {
        return costDAO.findById(id);
    }

    public void updateCost(Cost cost) {
        costValidator.validateCost(cost);
        costDAO.save(cost);
    }

    public void deleteCost(Cost cost) {
        costDAO.delete(cost);
    }

}
