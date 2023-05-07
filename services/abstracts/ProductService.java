package com.etiya.ecommerce.services.abstracts;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.Result;
import com.etiya.ecommerce.entities.concrete.Product;
import com.etiya.ecommerce.services.dtos.requests.product.AddProductRequest;
import com.etiya.ecommerce.services.dtos.requests.product.DeleteProductRequest;
import com.etiya.ecommerce.services.dtos.requests.product.UpdateProductRequest;
import com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.GetByIdCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.product.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


import java.util.List;

public interface ProductService {

    DataResult<AddProductResponse> add(AddProductRequest addProductRequest);

    DataResult<GetByIdProductResponse> getById(Integer id);

    DataResult<List<ListProductResponse>> getAll();

    DataResult<UpdateProductResponse>updateProduct(int id, UpdateProductRequest request);

    DataResult<DeleteProductResponse>deleteProduct(DeleteProductRequest request);

    DataResult<Slice<ListProductResponse>>getWithPagination(Pageable pageable);

}
