package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.internationalization.MessageManager;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperManager;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.CustomerOrderProduct;
import com.etiya.ecommerce.repositories.abstracts.ICustomerOrderProductDao;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.customerOrderProduct.AddCustomerOrderProductRequest;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.AddCustomerOrderProductResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.ResourceBundleMessageSource;

import static org.mockito.Mockito.mock;

class CustomerOrderProductManagerTest {

      ICustomerOrderProductDao customerOrderProductDao;
      ProductManager productManager;
      ModelMapperService mapperService;
      CustomerOrderManager customerOrderManager;
      MessageService messageService;

      CustomerOrderProductManager customerOrderProductManager;



    @BeforeEach
    void setUp() {
     productManager=mock(ProductManager.class);
     customerOrderManager=mock(CustomerOrderManager.class);
     customerOrderProductDao=mock(ICustomerOrderProductDao.class);
     mapperService=new ModelMapperManager(new ModelMapper());
     messageService=new MessageManager(bundleMessageSource());
     customerOrderProductManager=new CustomerOrderProductManager(customerOrderProductDao,productManager,mapperService,customerOrderManager,messageService);
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
    void customerOrderProductAddTest(){
        AddCustomerOrderProductRequest addCustomerOrderProductRequest=AddCustomerOrderProductRequest.builder()
                .orderId(1)
                .productId(1)
                .comments("Test-Comments")
                .quantity(2)
                .build();

        customerOrderManager.customerOrderId(addCustomerOrderProductRequest.getOrderId());
        productManager.productId(addCustomerOrderProductRequest.getProductId());

        CustomerOrderProduct customerOrderProduct = mapperService.forResponse()
                .map(addCustomerOrderProductRequest, CustomerOrderProduct.class);

        customerOrderProductDao.save(customerOrderProduct);

        AddCustomerOrderProductResponse customerOrderProductResponse = mapperService.forResponse().map(customerOrderProduct, AddCustomerOrderProductResponse.class);
        DataResult<AddCustomerOrderProductResponse>actualResponse=new SuccessDataResult<>(customerOrderProductResponse,Messages.CustomerOrderProduct.customerOrderProductAdd);

        DataResult<AddCustomerOrderProductResponse>expectedResponse=customerOrderProductManager.addCustomerOrderProduct(addCustomerOrderProductRequest);
        Assertions.assertEquals(actualResponse.getData(),expectedResponse.getData());
    }


}