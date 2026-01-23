package com.example.lestock.service;
import com.example.lestock.dao.MaterialTypeDAO;
import com.example.lestock.model.MaterialType;
import com.example.lestock.model.User;
import com.example.lestock.security.SecurityService;
import com.example.lestock.validator.MaterialTypeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class MaterialTypeServiceTest {
    @Mock
    private MaterialTypeDAO materialTypeDAO;

    @Mock
    private MaterialTypeValidator materialTypeValidator;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private MaterialTypeService materialTypeService;

    MaterialType materialType;
    User user;
    List<MaterialType> materialTypes =  new ArrayList<>();

    @BeforeEach
    void setUp() {
        materialType = new MaterialType("type", "metricUnit", "brand");
        materialTypes = List.of(materialType);
        user =  new User();
        user.setId(1L);
    }

    @Test
    void shouldReturnAllMaterialTypes() {
        //Arrange
        Mockito.when(materialTypeDAO.findAll()).thenReturn(materialTypes);

        //Act
        List<MaterialType> result = materialTypeService.getMaterialTypes();

        //Assert
        assertEquals(1, result.size());
        Mockito.verify(materialTypeDAO, Mockito.times(1)).findAll();
    }

    @Test
    void shouldSaveMaterialTypeSuccessfully() {
        //Arrange
        Mockito.when(materialTypeDAO.save(Mockito.any())).thenReturn(materialType);
        Mockito.doNothing().when(materialTypeValidator).validateMaterialType(Mockito.any());
        Mockito.when(securityService.getLoggedUser()).thenReturn(user);

        //Act
        materialTypeService.saveMaterialType(materialType);

        //Assert
        Mockito.verify(materialTypeDAO, Mockito.times(1)).save(materialType);
        Mockito.verify(materialTypeValidator, Mockito.times(1)).validateMaterialType(materialType);
        Mockito.verify(securityService, Mockito.times(1)).getLoggedUser();
    }

    @Test
    void shouldReturnAMaterialType() {
        //Arrange
        materialType.setId(1L);
        Mockito.when(materialTypeDAO.findById(1L)).thenReturn(Optional.of(materialType));

        //Act
        Optional<MaterialType> result = materialTypeService.getMaterialType(1L);

        //Assert
        assertEquals("type", result.get().getName());
        Mockito.verify(materialTypeDAO, Mockito.times(1)).findById(1L);
    }

    @Test
    void shouldUpdateMaterialTypeSuccessfully() {
        //Arrange
        Mockito.when(materialTypeDAO.save(materialType)).thenReturn(materialType);
        Mockito.doNothing().when(materialTypeValidator).validateMaterialType(Mockito.any());
        Mockito.when(securityService.getLoggedUser()).thenReturn(user);

        //Act
        materialTypeService.updateMaterialType(materialType);

        //Assert
        Mockito.verify(materialTypeDAO, Mockito.times(1)).save(materialType);
        Mockito.verify(materialTypeValidator, Mockito.times(1)).validateMaterialType(materialType);
        Mockito.verify(securityService, Mockito.times(1)).getLoggedUser();
    }

    @Test
    void shouldDeleteAMaterialType() {
        //Arrange


        //Act
        materialTypeService.deleteMaterialType(materialType);

        //Assert
        Mockito.verify(materialTypeDAO, Mockito.times(1)).delete(materialType);
    }




}