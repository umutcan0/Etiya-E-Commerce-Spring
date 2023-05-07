package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageManager;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperManager;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Supplier;
import com.etiya.ecommerce.repositories.abstracts.ISupplierDao;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.supplier.AddSupplierRequest;
import com.etiya.ecommerce.services.dtos.responses.supplier.AddSupplierResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SupplierManagerTest {

    private ISupplierDao supplierDao;

    private ModelMapperService mapperService;

    private MessageService messageService;

    private SupplierManager supplierManager;


    @BeforeEach
    void setUp() {
        supplierDao = mock(ISupplierDao.class);

        mapperService = new ModelMapperManager(new ModelMapper());

        messageService = new MessageManager(bundleMessageSource());

        supplierManager = new SupplierManager(supplierDao, mapperService, messageService);
    }


    ResourceBundleMessageSource bundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        return messageSource;
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    void addSupplier() {
        AddSupplierRequest request = AddSupplierRequest.builder()
                .name("Test-Name")
                .surname("Test-Surname")
                .details("Test-Details")
                .build();


        Supplier supplier = mapperService.forRequest().map(request, Supplier.class);
        supplierDao.save(supplier);

        AddSupplierResponse supplierResponse = mapperService.forResponse().map(supplier, AddSupplierResponse.class);


        DataResult<AddSupplierResponse> actualResponse = supplierManager.add(request);

        DataResult<AddSupplierResponse> dataResult = new SuccessDataResult<>(supplierResponse, Messages.Supplier.supplierAdd);

        Assertions.assertEquals(dataResult.getData(), actualResponse.getData());

    }

    @Test
    void getByIdWithNonExistingIdShouldThrowException() {

        when(supplierDao.findById(any())).thenReturn(Optional.ofNullable(null));
        int id = 2;
        Assertions.assertThrows(NotFoundException.class, () -> {
            supplierManager.getById(id);
        });
    }
}
