package com.example.lestock.validator;

import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.exceptions.DuplicateRecordException;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MaterialTypeValidatorTest {

    @Mock
    MaterialTypeDAO materialTypeDAO;

    @InjectMocks
    MaterialTypeValidator materialTypeValidator;

    MaterialType savedMaterialType;
    MaterialType newMaterialType;
    @BeforeEach
    void setUp() {
        newMaterialType = new MaterialType("newMaterialType", "", "");

        savedMaterialType = new MaterialType("savedMaterialType", "metricUnit", "brand");
        savedMaterialType.setId(1L);
    }

    @Test
    void shouldValidateANonSavedMaterialTypeWithoutIdCorrectly() {
        //Arrange
        Mockito.when(materialTypeDAO.findByNameAndBrand("newMaterialType","" )).thenReturn(Optional.empty());

        //Act


        //Assert
        assertDoesNotThrow(() -> materialTypeValidator.validateMaterialType(newMaterialType));
        Mockito.verify(materialTypeDAO, Mockito.times(1)).findByNameAndBrand("newMaterialType", "");
    }

    @Test
    void shouldGiveErrorWhenTryingToSaveAMaterialTypeWithNameAndBrandAlreadyTaken() {
        //Arrange
        Mockito.when(materialTypeDAO.findByNameAndBrand("savedMaterialType", "brand")).thenReturn(Optional.of(savedMaterialType));
        newMaterialType.setName("savedMaterialType");
        newMaterialType.setBrand("brand");

        //Act



        //Assert
        assertThrows(DuplicateRecordException.class, () -> materialTypeValidator.validateMaterialType(newMaterialType));
        Mockito.verify(materialTypeDAO, Mockito.times(1)).findByNameAndBrand("savedMaterialType", "brand");
    }

    @Test
    void shouldValidateASavedMaterialTypeCorrectly() {
        //Arrange
        Mockito.when(materialTypeDAO.findByNameAndBrand("savedMaterialType", "brand")).thenReturn(Optional.of(savedMaterialType));
        newMaterialType.setName("savedMaterialType");
        newMaterialType.setBrand("brand");
        newMaterialType.setId(1L);

        //Act



        //Assert
        assertDoesNotThrow(() -> materialTypeValidator.validateMaterialType(newMaterialType));
        Mockito.verify(materialTypeDAO, Mockito.times(1)).findByNameAndBrand("savedMaterialType",  "brand");
    }

    @Test
    void shouldGiveErrorWhenTryingToUpdateASavedMaterialTypeWithNameAndBrandAlreadyTaken() {
        //Arrange
        Mockito.when(materialTypeDAO.findByNameAndBrand("savedMaterialType", "brand")).thenReturn(Optional.of(savedMaterialType));
        newMaterialType.setName("savedMaterialType");
        newMaterialType.setBrand("brand");
        newMaterialType.setId(2L);

        //Act



        //Assert
        assertThrows(DuplicateRecordException.class, () -> materialTypeValidator.validateMaterialType(newMaterialType));
        Mockito.verify(materialTypeDAO, Mockito.times(1)).findByNameAndBrand("savedMaterialType",  "brand");
    }


}