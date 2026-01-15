package com.example.lestock.service;
import com.example.lestock.dao.SupplierDAO;
import com.example.lestock.model.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SupplierServiceTest {

    @Mock
    private SupplierDAO supplierDAO;

    @InjectMocks
    private SupplierService supplierService;

    Supplier supplier;
    Supplier supplier2;
    List<Supplier> suppliers =  new ArrayList<>();

    @BeforeEach
    void setUp() {
        supplier = new Supplier("supplier1", "description1", "contact1", "socialMedia1", "address1");
        supplier2 = new Supplier("supplier2", "description2", "contact2", "socialMedia2", "address2");
        suppliers = List.of(supplier, supplier2);
    }

    @Test
    void shouldReturnAllSuppliers() {
        //Arrange
        Mockito.when(supplierDAO.findAll()).thenReturn(suppliers);

        //Act
        List<Supplier> result = supplierService.getSuppliers();

        //Assert
        assertEquals(result.size(), suppliers.size());
        Mockito.verify(supplierDAO, Mockito.times(1)).findAll();
    }



}