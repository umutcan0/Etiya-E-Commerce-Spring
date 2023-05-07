package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.ProductCategory;
import com.etiya.ecommerce.repositories.abstracts.IProductCategoryDao;
import com.etiya.ecommerce.services.abstracts.ProductCategoryService;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.productCategory.AddProductCategoryRequest;
import com.etiya.ecommerce.services.dtos.requests.productCategory.UpdateProductCategoryRequest;
import com.etiya.ecommerce.services.dtos.responses.productCategory.AddProductCategoryResponse;
import com.etiya.ecommerce.services.dtos.responses.productCategory.ListProductCategoryResponse;
import com.etiya.ecommerce.services.dtos.responses.productCategory.UpdateProductCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductCategoryManager implements ProductCategoryService {

    private final IProductCategoryDao dao;
    private MessageService messageService;
    private ModelMapperService mapperService;


    @Override
    public DataResult<List<ListProductCategoryResponse>> getAll() {

        List<ProductCategory> productCategoryList = dao.findAllByProductCategory();

        List<ListProductCategoryResponse> productCategoryResponses = productCategoryList.
                stream().
                map(productCategory -> {
                    ListProductCategoryResponse response = mapperService.forResponse().map(productCategory, ListProductCategoryResponse.class);
                    return response;
                }).collect(Collectors.toList());

        return new SuccessDataResult<>(productCategoryResponses,messageService.getMessage(Messages.Category.CategoryListed));
    }

    @Override
    public DataResult<AddProductCategoryResponse> add(AddProductCategoryRequest addProductCategoryRequest) {
        ProductCategory productCategory = new ProductCategory();
        productCategory = mapperService.forRequest().map(addProductCategoryRequest, ProductCategory.class);
        dao.save(productCategory);

        AddProductCategoryResponse addProductCategoryResponse = mapperService.forResponse().map(productCategory, AddProductCategoryResponse.class);
        return new SuccessDataResult<>(addProductCategoryResponse,messageService.getMessage(Messages.Category.categoryAdd));
    }

    @Override
    public DataResult<UpdateProductCategoryResponse> update(int id, UpdateProductCategoryRequest request) {
        ProductCategory productCategory = dao.findById(id)
                .orElseThrow(()->new NotFoundException(messageService.getMessageWithParams(Messages.Category.categoryNotFound,id)));
        BeanUtils.copyProperties(request,productCategory);

        dao.save(productCategory);

        UpdateProductCategoryResponse response = mapperService.forResponse().map(productCategory, UpdateProductCategoryResponse.class);
        return new SuccessDataResult<>(response, messageService.getMessage(Messages.Category.categoryUpdate));

    }


    protected ProductCategory getProductCategoryId(Integer id) {
        ProductCategory productCategory = dao.findById(id)
                .orElseThrow(()->new NotFoundException(messageService.getMessageWithParams(Messages.Category.categoryNotFound,id)));
        return productCategory;
    }

    @Override
    public DataResult<Slice<ListProductCategoryResponse>> getWithPagination(Pageable pageable) {
        return new SuccessDataResult<>(dao.getAll(pageable), messageService.getMessage(Messages.Category.CategoryListed));
    }
}
