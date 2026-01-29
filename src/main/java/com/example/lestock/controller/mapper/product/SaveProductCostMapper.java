package com.example.lestock.controller.mapper.product;
import com.example.lestock.controller.dto.product.SaveProductCostDTO;
import com.example.lestock.dao.product.CostDAO;
import com.example.lestock.model.product.Cost;
import com.example.lestock.model.product.ProductCost;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SaveProductCostMapper {
    @Autowired
    CostDAO costDAO;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "cost", ignore = true)
    public abstract ProductCost toEntity(SaveProductCostDTO dto);

    @AfterMapping
    protected void resolveCost(SaveProductCostDTO dto, @MappingTarget ProductCost entity) {
        if(dto.costId() != null) {
            Cost cost = costDAO.findById(dto.costId()).orElse(null);
            entity.setCost(cost);
        }
    }
}
