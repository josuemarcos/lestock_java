package com.example.lestock.validator;

import com.example.lestock.dao.SupplierDAO;
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
class SupplierValidatorTest {

    @Mock
    SupplierDAO supplierDAO;

    @InjectMocks
    SupplierValidator supplierValidator;

    Supplier savedSupplier;
    Supplier newSupplier;
    @BeforeEach
    void setUp() {
        newSupplier = new Supplier("newSupplier", "", "", "", "");

        savedSupplier = new Supplier("savedSupplier", "savedSupplier", "savedSupplierContact",
                "savedSupplierSocialMedia", "savedSupplierAddress");
        savedSupplier.setId(1L);
    }

    @Test
    void shouldValidateANonSavedSupplierWithoutIdCorrectly() {
        //Arrange
        Mockito.when(supplierDAO.findByName("newSupplier")).thenReturn(Optional.empty());

        //Act

        

        //Assert
        assertDoesNotThrow(() -> supplierValidator.validateSupplier(newSupplier));
        Mockito.verify(supplierDAO, Mockito.times(1)).findByName("newSupplier");
    }

}