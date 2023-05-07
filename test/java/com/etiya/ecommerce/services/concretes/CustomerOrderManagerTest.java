package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageManager;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperManager;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Customer;
import com.etiya.ecommerce.entities.concrete.CustomerOrder;
import com.etiya.ecommerce.repositories.abstracts.CustomerOrderDao;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.customerOrder.AddCustomerOrderRequest;
import com.etiya.ecommerce.services.dtos.responses.customerOrder.AddCustomerOrderResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Optional;

import static org.mockito.Mockito.*;

class CustomerOrderManagerTest {

    CustomerOrderDao customerOrderDao;

    ModelMapperService modelMapperService;

    MessageService messageService;

    CustomerManager customerManager;

    CustomerOrderManager customerOrderManager;

    @BeforeEach
    void setUp() {

        customerOrderDao = mock(CustomerOrderDao.class);
        modelMapperService = new ModelMapperManager(new ModelMapper());
        messageService = new MessageManager(bundleMessageSource());
        customerManager = mock(CustomerManager.class);
        customerOrderManager = new CustomerOrderManager(customerOrderDao, modelMapperService,customerManager,messageService);
    }

    ResourceBundleMessageSource bundleMessageSource(){
        ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        return messageSource;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void add() {
        AddCustomerOrderRequest request = AddCustomerOrderRequest.builder()
                .statusCode("Test-Status_Code")
                .placed("Test-Placed")
                .paid("Test-Paid")
                .price("Test-Price")
                .details("Test-Details")
                .customerId(1)
                .build();

        Customer customer = customerManager.customerId(request.getCustomerId());


        CustomerOrder customerOrder = modelMapperService.forRequest().map(request, CustomerOrder.class);
         customerOrderDao.save(customerOrder);

        AddCustomerOrderResponse response = modelMapperService.forResponse().map(customerOrder, AddCustomerOrderResponse.class);


        DataResult<AddCustomerOrderResponse>dataResult=new SuccessDataResult<>(response,Messages.CustomerOrder.customerOrderAdd);

        DataResult<AddCustomerOrderResponse>customerOrderResponseDataResult=customerOrderManager.add(request);
        Assertions.assertEquals(dataResult.getData(), customerOrderResponseDataResult.getData());
    }

    @Test
    void getByIdWithNonExistingIdShouldThrowException() {

        when(customerOrderDao.findById(any())).thenReturn(Optional.ofNullable(null));
        int customerOrderId = 3;
        Assertions.assertThrows(NotFoundException.class, () -> {
            customerOrderManager.getById(customerOrderId);
        });

    }


}
