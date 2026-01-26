package com.example.lestock.controller.product;
import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.product.CostDTO;
import com.example.lestock.controller.mapper.product.CostMapper;
import com.example.lestock.model.product.Cost;
import com.example.lestock.service.product.CostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/costs")
@RequiredArgsConstructor
public class CostController implements GenericController {

    private final CostService costService;
    private final CostMapper costMapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> saveCost(@RequestBody @Valid CostDTO cost) {
        Cost costEntity = costMapper.toEntity(cost);
        costService.saveCost(costEntity);
        URI location = generateHeaderLocation(costEntity.getId());
        return ResponseEntity.created(location).build();
    }
}
