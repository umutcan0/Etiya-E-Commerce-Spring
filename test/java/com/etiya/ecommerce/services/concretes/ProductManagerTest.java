package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageManager;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperManager;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Product;
import com.etiya.ecommerce.repositories.abstracts.IProductDao;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.product.AddProductRequest;
import com.etiya.ecommerce.services.dtos.responses.product.AddProductResponse;
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

public class ProductManagerTest {

    private IProductDao productDao;

    private ModelMapperService mapperService;

    private MessageService messageService;

    private ProductManager productManager;

    private ProductCategoryManager productCategoryManager;


    @BeforeEach
    void setUp() {
        productDao = mock(IProductDao.class);

        mapperService = new ModelMapperManager(new ModelMapper());

        messageService = new MessageManager(bundleMessageSource());

        productCategoryManager= mock(ProductCategoryManager.class);

        productManager = new ProductManager(productDao, mapperService, messageService, productCategoryManager);
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
    void add() {
        AddProductRequest request = AddProductRequest.builder()
                .price("Test-Price")
                .details("Test-Details")
                .name("Test-Name")
                .categoryId(1)
                .build();


        Product product = mapperService.forRequest().map(request, Product.class);
        productDao.save(product);

        AddProductResponse productResponse = mapperService.forResponse().map(product, AddProductResponse.class);


        DataResult<AddProductResponse> actualResponse = productManager.add(request);

        DataResult<AddProductResponse> dataResult = new SuccessDataResult<>(productResponse, Messages.Product.productAdd);

        Assertions.assertEquals(dataResult.getData(), actualResponse.getData());

    }

    @Test
    void getByIdWithNonExistingIdShouldThrowException() {

        when(productDao.findById(any())).thenReturn(Optional.ofNullable(null));
        int id = 2;
        Assertions.assertThrows(NotFoundException.class, () -> {
            productManager.getById(id);
        });
    }}

