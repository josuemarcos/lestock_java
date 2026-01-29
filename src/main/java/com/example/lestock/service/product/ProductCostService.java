package com.example.lestock.service.product;
import com.example.lestock.dao.product.ProductCostDAO;
import com.example.lestock.model.product.ProductCost;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCostService {
    private final ProductCostDAO  productCostDAO;

    public void save(ProductCost productCost) {
        productCostDAO.save(productCost);
    }
}
