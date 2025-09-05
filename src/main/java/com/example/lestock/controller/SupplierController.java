package com.example.lestock.controller;
import com.example.lestock.controller.dto.SupplierDTO;
import com.example.lestock.controller.mapper.SupplierMapper;
import com.example.lestock.model.Supplier;
import com.example.lestock.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController implements GenericController {
    private final SupplierService supplierService;
    private final SupplierMapper supplierMapper;

    @PostMapping
    public ResponseEntity<Void> saveSupplier(@RequestBody SupplierDTO supplierDTO) {
        Supplier supplierEntity = supplierMapper.toEntity(supplierDTO);
        supplierService.saveSupplier(supplierEntity);
        URI location = generateHeaderLocation(supplierEntity.getId());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<SupplierDTO>> getSuppliers(){
        List<SupplierDTO> suppliers = supplierService.getSuppliers()
                .stream()
                .map(supplierMapper::toDTO)
                .toList();
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("{id}")
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
}
