package com.example.lestock.controller;
import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.stock.GetSupplierDTO;
import com.example.lestock.controller.dto.stock.SaveSupplierDTO;
import com.example.lestock.controller.mapper.stock.SupplierMapper;
import com.example.lestock.model.User;
import com.example.lestock.model.stock.Supplier;
import com.example.lestock.security.annotation.LoggedUser;
import com.example.lestock.service.supplier.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
@Tag(name = "Suppliers")
public class SupplierController implements GenericController {
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @Operation(summary = "Save", description = "Register a new supplier")
    public ResponseEntity<Void> saveSupplier(@RequestBody @Valid SaveSupplierDTO supplierDTO, @LoggedUser User loggedUser) {
        Supplier savedSupplier = supplierService.saveSupplier(supplierMapper.toEntity(supplierDTO), loggedUser);
        URI location = generateHeaderLocation(savedSupplier.getId());
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    @Operation(summary = "Get all Suppliers", description = "Retrieve all suppliers from database")
    public ResponseEntity<List<GetSupplierDTO>> getSuppliers(){
        List<GetSupplierDTO> suppliers = supplierService.getSuppliers()
                .stream()
                .map(supplierMapper::toDTO)
                .toList();
        return ResponseEntity.ok(suppliers);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("{supplierId}")
    @Operation(summary = "Get Supplier", description = "Retrieve a supplier from database")
    public ResponseEntity<GetSupplierDTO> getSupplier(@PathVariable Long supplierId){
        return  ResponseEntity.ok(supplierMapper.toDTO(supplierService.getSupplierById(supplierId)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{supplierId}")
    @Operation(summary = "Update Supplier", description = "Update a supplier")
    public ResponseEntity<Void> updateSupplier(@PathVariable Long supplierId, @RequestBody @Valid SaveSupplierDTO supplierDTO,
                                                 @LoggedUser User loggedUser){
        supplierService.updateSupplier(supplierId, supplierDTO, loggedUser);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{supplierId}")
    @Operation(summary = "Delete Supplier", description = "Delete a supplier")
    public ResponseEntity<Object> deleteSupplier(@PathVariable Long supplierId) {
        supplierService.deleteSupplier(supplierId);
        return ResponseEntity.noContent().build();
    }
}
