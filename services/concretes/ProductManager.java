package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Product;
import com.etiya.ecommerce.repositories.abstracts.IProductDao;
import com.etiya.ecommerce.services.abstracts.ProductService;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.product.AddProductRequest;
import com.etiya.ecommerce.services.dtos.requests.product.DeleteProductRequest;
import com.etiya.ecommerce.services.dtos.requests.product.UpdateProductRequest;
import com.etiya.ecommerce.services.dtos.responses.product.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {

    private IProductDao productDao;
    private ModelMapperService mapperService;
    private MessageService messageService;
    private ProductCategoryManager productCategoryManager;

    @Override
    public DataResult<GetByIdProductResponse> getById(Integer id) {
        Product product = productDao.findById(id).orElseThrow(
                () -> new NotFoundException(messageService.getMessageWithParams(Messages.Product.productNotFound))
        );
        GetByIdProductResponse response = mapperService.forResponse().map(product, GetByIdProductResponse.class);
        return new SuccessDataResult<>(response,messageService.getMessage(Messages.Product.productWithId));
    }

    @Override
    public DataResult<AddProductResponse> add(AddProductRequest addProductRequest) {

        productCategoryManager.getProductCategoryId(addProductRequest.getCategoryId());
        Product product1 = new Product();
        product1 = mapperService.forRequest().map(addProductRequest, Product.class);
        productDao.save(product1);
        AddProductResponse addProductResponse = mapperService.forResponse().map(product1, AddProductResponse.class);
        return new SuccessDataResult<>(addProductResponse, messageService.getMessage(Messages.Product.productAdd));
    }

    @Override
    public DataResult<UpdateProductResponse> updateProduct(int id, UpdateProductRequest request) {

        Product product = productDao.findById(id).orElseThrow(
                () -> new NotFoundException(messageService.getMessageWithParams(Messages.Product.productNotFound, id))
        );

        BeanUtils.copyProperties(request, product);

        productDao.save(product);

        UpdateProductResponse response = mapperService.forResponse().map(product, UpdateProductResponse.class);
        return new SuccessDataResult<>(response, messageService.getMessage(Messages.Product.productUpdate));
    }

    @Override
    public DataResult<DeleteProductResponse> deleteProduct(DeleteProductRequest request) {

        Product product = productDao.findById(request.getId()).orElseThrow(
                () -> new NotFoundException(messageService.getMessageWithParams(Messages.Product.productNotFound, request.getId()))
        );

        DeleteProductResponse response = mapperService.forResponse().map(product, DeleteProductResponse.class);
        return new SuccessDataResult<>(response, messageService.getMessage(Messages.Product.productDelete));
    }

    @Override
    public DataResult<List<ListProductResponse>> getAll() {
        List<Product> productsList = productDao.findAll();

        List<ListProductResponse> productResponses = productsList.
                stream().
                map(product -> {

                    ListProductResponse productResponse = mapperService.forResponse().map(product, ListProductResponse.class);
                    return productResponse;

                }).collect(Collectors.toList());

        return new SuccessDataResult<>(productResponses, messageService.getMessage(Messages.Product.productListed));
    }

    @Override
    public DataResult<Slice<ListProductResponse>> getWithPagination(Pageable pageable) {
        return new SuccessDataResult<>(productDao.getAll(pageable));
    }

    protected Product productId(Integer productID) {
       Product product = productDao.findById(productID).orElseThrow(
               () -> new NotFoundException(messageService.getMessageWithParams(Messages.Product.productNotFound, productID))
       );
        return product;}


}
