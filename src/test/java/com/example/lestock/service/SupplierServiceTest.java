package com.example.lestock.service;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.model.Supplier;
import com.example.lestock.model.User;
import com.example.lestock.security.SecurityService;
import com.example.lestock.validator.SupplierValidator;
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
class SupplierServiceTest {

    @Mock
    private SupplierDAO supplierDAO;

    @Mock
    private SupplierValidator supplierValidator;

    @Mock
    private SecurityService securityService;

    @InjectMocks
    private SupplierService supplierService;

    Supplier supplier;
    User user;
    List<Supplier> suppliers =  new ArrayList<>();

    @BeforeEach
    void setUp() {
        supplier = new Supplier("supplier1", "description1", "contact1", "socialMedia1", "address1");
        suppliers = List.of(supplier);
        user =  new User();
        user.setId(1L);
    }

    @Test
    void shouldReturnAllSuppliers() {
        //Arrange
        Mockito.when(supplierDAO.findAll()).thenReturn(suppliers);

        //Act
        List<Supplier> result = supplierService.getSuppliers();

        //Assert
        assertEquals(1, result.size());
        Mockito.verify(supplierDAO, Mockito.times(1)).findAll();
    }

    @Test
    void shouldSaveSupplierSuccessfully() {
        //Arrange
        Mockito.when(supplierDAO.save(Mockito.any())).thenReturn(supplier);
        Mockito.doNothing().when(supplierValidator).validateSupplier(Mockito.any());
        Mockito.when(securityService.getLoggedUser()).thenReturn(user);

        //Act
        supplierService.saveSupplier(supplier);

        //Assert
        Mockito.verify(supplierDAO, Mockito.times(1)).save(supplier);
        Mockito.verify(supplierValidator, Mockito.times(1)).validateSupplier(supplier);
        Mockito.verify(securityService, Mockito.times(1)).getLoggedUser();
    }

    @Test
    void shouldReturnASupplier() {
        //Arrange
        supplier.setId(1L);
        Mockito.when(supplierDAO.findById(1L)).thenReturn(Optional.of(supplier));

        //Act
        Optional<Supplier> result = supplierService.getSupplierById(1L);

        //Assert
        assertEquals("supplier1", result.get().getName());
        Mockito.verify(supplierDAO, Mockito.times(1)).findById(1L);
    }

    @Test
    void shouldUpdateSupplierSuccessfully() {
        //Arrange
        Mockito.when(supplierDAO.save(supplier)).thenReturn(supplier);
        Mockito.doNothing().when(supplierValidator).validateSupplier(Mockito.any());
        Mockito.when(securityService.getLoggedUser()).thenReturn(user);

        //Act
        supplier.setName("newName");
        supplierService.updateSupplier(supplier);

        //Assert
        Mockito.verify(supplierDAO, Mockito.times(1)).save(supplier);
        Mockito.verify(supplierValidator, Mockito.times(1)).validateSupplier(supplier);
        Mockito.verify(securityService, Mockito.times(1)).getLoggedUser();
    }



}