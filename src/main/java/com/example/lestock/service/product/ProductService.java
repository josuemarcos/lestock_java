package com.example.lestock.service.product;
import com.example.lestock.dao.product.CostDAO;
import com.example.lestock.dao.product.ProductDAO;
import com.example.lestock.dao.stock.MaterialDAO;
import com.example.lestock.model.product.Cost;
import com.example.lestock.model.product.Product;
import com.example.lestock.model.stock.Material;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;
    private final CostDAO costDAO;
    private final MaterialDAO materialDAO;

    public void save(Product product) {
        product.getProductCosts().forEach(pc -> {
            Cost cost = costDAO.findById(pc.getCost().getId()).orElse(null);
            pc.setCost(cost);
            pc.setProduct(product);
        });

        product.getProductMaterials().forEach(pm -> {
            Material material = materialDAO.findById(pm.getMaterial().getId()).orElse(null);
            pm.setMaterial(material);
            pm.setProduct(product);
        });

        productDAO.save(product);
    }


    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }



}
