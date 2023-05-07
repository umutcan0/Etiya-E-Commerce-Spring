package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageManager;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperManager;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.ProductCategory;
import com.etiya.ecommerce.repositories.abstracts.IProductCategoryDao;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.productCategory.AddProductCategoryRequest;
import com.etiya.ecommerce.services.dtos.responses.productCategory.AddProductCategoryResponse;
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

public class ProductCategoryManagerTest {

    private IProductCategoryDao productCategoryDao;

    private ModelMapperService mapperService;

    private MessageService messageService;

    private ProductCategoryManager productCategoryManager;


    @BeforeEach
    void setUp() {
        productCategoryDao = mock(IProductCategoryDao.class);

        mapperService = new ModelMapperManager(new ModelMapper());

        messageService = new MessageManager(bundleMessageSource());

        productCategoryManager = new ProductCategoryManager(productCategoryDao, messageService, mapperService);
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
    void addProductCategory() {
        AddProductCategoryRequest request = AddProductCategoryRequest.builder()
                .name("Test-Name")
                .build();


        ProductCategory productCategory = mapperService.forRequest().map(request, ProductCategory.class);
        productCategoryDao.save(productCategory);

        AddProductCategoryResponse productCategoryResponse = mapperService.forResponse().map(productCategory, AddProductCategoryResponse.class);


        DataResult<AddProductCategoryResponse> actualResponse = productCategoryManager.add(request);

        DataResult<AddProductCategoryResponse> dataResult = new SuccessDataResult<>(productCategoryResponse, Messages.Category.categoryAdd);

        Assertions.assertEquals(dataResult.getData(), actualResponse.getData());

    }

    @Test
    void getByIdWithNonExistingIdShouldThrowException() {

        when(productCategoryDao.findById(any())).thenReturn(Optional.ofNullable(null));
        int id = 2;
        Assertions.assertThrows(NotFoundException.class, () -> {
            productCategoryManager.getProductCategoryId(id);
        });
    }
}
