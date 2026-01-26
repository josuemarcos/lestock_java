package com.example.lestock.controller.mapper.stock;
import com.example.lestock.controller.dto.stock.SupplierDTO;
import com.example.lestock.model.stock.Supplier;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SupplierMapper {
    Supplier toEntity(SupplierDTO supplierDTO);
    SupplierDTO toDTO(Supplier supplier);
}
