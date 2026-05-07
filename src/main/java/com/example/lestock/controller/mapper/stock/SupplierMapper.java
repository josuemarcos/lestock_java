package com.example.lestock.controller.mapper.stock;
import com.example.lestock.controller.dto.stock.GetSupplierDTO;
import com.example.lestock.controller.dto.stock.SaveSupplierDTO;
import com.example.lestock.model.stock.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    Supplier toEntity(SaveSupplierDTO supplierDTO);

    GetSupplierDTO toDTO(Supplier supplier);

    void updateEntity(@MappingTarget Supplier supplier, SaveSupplierDTO saveSupplierDTO);
}
