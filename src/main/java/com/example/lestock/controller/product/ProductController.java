package com.example.lestock.controller.product;
import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.product.GetProductDTO;
import com.example.lestock.controller.dto.product.SaveProductDTO;
import com.example.lestock.controller.mapper.product.GetProductMapper;
import com.example.lestock.controller.mapper.product.SaveProductMapper;
import com.example.lestock.model.product.Product;
import com.example.lestock.service.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController implements GenericController {
    private final ProductService productService;
    private final SaveProductMapper saveProductMapper;
    private final GetProductMapper getProductMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> saveProduct(@RequestBody SaveProductDTO dto) {
        Product productEntity = saveProductMapper.toEntity(dto);
        System.out.println(productEntity.getProductCosts().size());
        System.out.println(productEntity.getProductMaterials().size());
        productService.save(productEntity);
        URI location = generateHeaderLocation(productEntity.getId());
        return  ResponseEntity.created(location).build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<GetProductDTO>> getAllProducts() {
        List<GetProductDTO> products = productService
                .getAllProducts()
                .stream()
                .map(getProductMapper::toDto)
                .toList();
        return ResponseEntity.ok(products);


    }

}
