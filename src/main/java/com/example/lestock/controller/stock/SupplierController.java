package com.example.lestock.controller.stock;
import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.stock.SupplierDTO;
import com.example.lestock.controller.mapper.stock.SupplierMapper;
import com.example.lestock.model.stock.Supplier;
import com.example.lestock.service.stock.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Supplier registered successfully"),
            @ApiResponse(responseCode = "422", description = "Validation Error"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    public ResponseEntity<Void> saveSupplier(@RequestBody @Valid SupplierDTO supplierDTO) {
        Supplier supplierEntity = supplierMapper.toEntity(supplierDTO);
        supplierService.saveSupplier(supplierEntity);
        URI location = generateHeaderLocation(supplierEntity.getId());
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    @Operation(summary = "Get all Suppliers", description = "Retrieve all suppliers from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Returned all suppliers registered"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    public ResponseEntity<List<SupplierDTO>> getSuppliers(){
        List<SupplierDTO> suppliers = supplierService.getSuppliers()
                .stream()
                .map(supplierMapper::toDTO)
                .toList();
        return ResponseEntity.ok(suppliers);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("{id}")
    @Operation(summary = "Get Supplier", description = "Retrieve a supplier from database")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Supplier found"),
            @ApiResponse(responseCode = "404", description = "Supplier not found"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    public ResponseEntity<SupplierDTO> getSupplier(@PathVariable Long id){
        return supplierService.getSupplierById(id)
                .map(
                        supplier -> {
                            SupplierDTO supplierDTO = supplierMapper.toDTO(supplier);
                            return ResponseEntity.ok(supplierDTO);
                        }
                )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    @Operation(summary = "Update Supplier", description = "Update a supplier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Supplier updated successfully"),
            @ApiResponse(responseCode = "404", description = "Supplier meant to be updated not found"),
            @ApiResponse(responseCode = "422", description = "Validation Error"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    public ResponseEntity<Object> updateSupplier(@PathVariable Long id, @RequestBody @Valid SupplierDTO supplierDTO){
        return supplierService.getSupplierById(id)
                .map(
                        supplier -> {
                            supplier.setName(supplierDTO.name());
                            supplier.setAddress(supplierDTO.address());
                            supplier.setContact(supplierDTO.contact());
                            supplier.setDescription(supplierDTO.description());
                            supplier.setSocialMedia(supplierDTO.socialMedia());
                            supplierService.updateSupplier(supplier);
                            return ResponseEntity.ok().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    @Operation(summary = "Delete Supplier", description = "Delete a supplier")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Supplier deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Supplier meant to be deleted not found"),
            @ApiResponse(responseCode = "401", description = "User not authenticated"),
            @ApiResponse(responseCode = "403", description = "User Role has no permission")
    })
    public ResponseEntity<Object> deleteSupplier(@PathVariable Long id) {
        return supplierService.getSupplierById(id)
                .map(
                        supplier -> {
                            supplierService.deleteSupplier(supplier);
                            return ResponseEntity.noContent().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
