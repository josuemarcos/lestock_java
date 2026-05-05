package com.example.lestock.controller.product;
import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.product.GetCostDTO;
import com.example.lestock.controller.dto.product.SaveCostDTO;
import com.example.lestock.controller.mapper.product.CostMapper;
import com.example.lestock.model.product.Cost;
import com.example.lestock.service.product.CostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "Costs")
@RestController
@RequestMapping("/costs")
@RequiredArgsConstructor
public class CostController implements GenericController {

    private final CostService costService;
    private final CostMapper costMapper;

    @Operation(summary = "Save cost", description = "Save a cost to database")
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> saveCost(@RequestBody @Valid SaveCostDTO cost) {
        Cost costEntity = costMapper.toEntity(cost);
        costService.saveCost(costEntity);
        URI location = generateHeaderLocation(costEntity.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Get all costs", description = "Return all saved costs")
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<GetCostDTO>> getAllCosts() {
        List<GetCostDTO> costs = costService.getAllCosts().stream().map(costMapper::toDTO).toList();
        return ResponseEntity.ok(costs);
    }

    @Operation(summary = "Get cost by id", description = "Return a specific cost by its id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<GetCostDTO> getCostById(@PathVariable Long id) {
        return costService
                .getCostById(id)
                .map(cost -> ResponseEntity.ok(costMapper.toDTO(cost)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Update cost", description = "Updated a saved cost")
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateCost(@PathVariable Long id, @RequestBody @Valid GetCostDTO cost) {
        return costService.getCostById(id).map(foundCost -> {
            foundCost.setName(cost.name());
            foundCost.setUnitPrice(cost.unitPrice());
            costService.updateCost(foundCost);
            return ResponseEntity.ok().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Delete cost", description = "Remove a saved cost")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteCost(@PathVariable Long id) {
        return costService.getCostById(id).map(foundCost -> {
            costService.deleteCost(foundCost);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
