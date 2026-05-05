package com.example.lestock.controller.stock;
import com.example.lestock.controller.common.GenericController;
import com.example.lestock.controller.dto.stock.GetSupplierDTO;
import com.example.lestock.controller.dto.stock.SaveSupplierDTO;
import com.example.lestock.controller.mapper.stock.SupplierMapper;
import com.example.lestock.model.User;
import com.example.lestock.model.stock.Supplier;
import com.example.lestock.security.annotation.LoggedUser;
import com.example.lestock.service.stock.SupplierService;
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
        Supplier supplierEntity = supplierMapper.toEntity(supplierDTO);
        supplierService.saveSupplier(supplierEntity, loggedUser);
        URI location = generateHeaderLocation(supplierEntity.getId());
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
    @GetMapping("{id}")
    @Operation(summary = "Get Supplier", description = "Retrieve a supplier from database")
    public ResponseEntity<GetSupplierDTO> getSupplier(@PathVariable Long id){
        return supplierService.getSupplierById(id)
                .map(
                        supplier -> {
                            GetSupplierDTO supplierDTO = supplierMapper.toDTO(supplier);
                            return ResponseEntity.ok(supplierDTO);
                        }
                )
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    @Operation(summary = "Update Supplier", description = "Update a supplier")
    public ResponseEntity<Object> updateSupplier(@PathVariable Long id, @RequestBody @Valid GetSupplierDTO supplierDTO,
                                                 @LoggedUser User loggedUser){
        return supplierService.getSupplierById(id)
                .map(
                        supplier -> {
                            supplier.setName(supplierDTO.name());
                            supplier.setAddress(supplierDTO.address());
                            supplier.setContact(supplierDTO.contact());
                            supplier.setDescription(supplierDTO.description());
                            supplier.setSocialMedia(supplierDTO.socialMedia());
                            supplierService.updateSupplier(supplier, loggedUser);
                            return ResponseEntity.ok().build();
                        }
                ).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{id}")
    @Operation(summary = "Delete Supplier", description = "Delete a supplier")
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
