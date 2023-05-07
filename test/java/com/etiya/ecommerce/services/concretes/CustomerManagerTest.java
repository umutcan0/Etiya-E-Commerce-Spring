package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageManager;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperManager;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Customer;
import com.etiya.ecommerce.repositories.abstracts.CustomerDao;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.customer.AddCustomerRequest;
import com.etiya.ecommerce.services.dtos.responses.customer.AddCustomerResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Optional;

import static org.mockito.Mockito.*;

class CustomerManagerTest {

    CustomerDao customerDao;
    ModelMapperService modelMapperService;
    MessageService messageService;
    CustomerManager customerManager;

    @BeforeEach
    void setUp() {

        customerDao = mock(CustomerDao.class);
        modelMapperService = new ModelMapperManager(new ModelMapper());
        messageService=new MessageManager(bundleMessageSource());
        customerManager = new CustomerManager(customerDao, modelMapperService, messageService);

    }

    public ResourceBundleMessageSource bundleMessageSource(){
        ResourceBundleMessageSource messageSource=new ResourceBundleMessageSource();
        messageSource.setBasename("message");
        return messageSource;
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void add() {
        AddCustomerRequest request = AddCustomerRequest.builder()
                .fullName("Test-Full-Name")
                .phone("Test-Phone")
                .email("Test-Email")
                .details("Test-Details")
                .build();

       Customer customer = modelMapperService.forRequest().map(request, Customer.class);
       customerDao.save(customer);

       AddCustomerResponse customerResponse = modelMapperService.forResponse().map(customer, AddCustomerResponse.class);

       DataResult<AddCustomerResponse> customerResponseDataResult = customerManager.add(request);
       DataResult<AddCustomerResponse> dataResult = new SuccessDataResult<>(customerResponse, Messages.Customer.customerAdd);

        Assertions.assertEquals(dataResult.getData(), customerResponseDataResult.getData());
    }

    @Test
    void getByIdWithNonExistingIdShouldThrowException() {

        when(customerDao.findById(any())).thenReturn(Optional.ofNullable(null));
        int customerId = 1;
        Assertions.assertThrows(NotFoundException.class, () -> {
            customerManager.getById(customerId);
        });

    }

}
