package com.example.lestock.service.product;
import com.example.lestock.dao.product.ProductDAO;
import com.example.lestock.model.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDAO productDAO;

    public void save(Product product) {
        productDAO.save(product);
    }
}
