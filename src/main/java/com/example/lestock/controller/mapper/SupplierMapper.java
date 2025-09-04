package com.example.lestock.controller.mapper;
import com.example.lestock.controller.dto.SupplierDTO;
import com.example.lestock.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(SupplierDTO supplierDTO);
    SupplierDTO toDTO(Supplier supplier);
}
