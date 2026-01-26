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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

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

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<CostDTO>> getAllCosts() {
        List<CostDTO> costs = costService.getAllCosts().stream().map(costMapper::toDTO).toList();
        return ResponseEntity.ok(costs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<CostDTO> getCostById(@PathVariable Long id) {
        return costService
                .getCostById(id)
                .map(cost -> ResponseEntity.ok(costMapper.toDTO(cost)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
