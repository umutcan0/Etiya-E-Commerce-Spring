package com.etiya.ecommerce.services.abstracts;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.entities.concrete.ProductCategory;
import com.etiya.ecommerce.services.dtos.requests.productCategory.AddProductCategoryRequest;
import com.etiya.ecommerce.services.dtos.requests.productCategory.UpdateProductCategoryRequest;
import com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.productCategory.AddProductCategoryResponse;
import com.etiya.ecommerce.services.dtos.responses.productCategory.ListProductCategoryResponse;
import com.etiya.ecommerce.services.dtos.responses.productCategory.UpdateProductCategoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryService {

    DataResult<List<ListProductCategoryResponse>> getAll();

    DataResult<AddProductCategoryResponse> add(AddProductCategoryRequest addProductCategoryRequest);

    DataResult<UpdateProductCategoryResponse> update(int id, UpdateProductCategoryRequest updateProductCategoryRequest);
    DataResult<Slice<ListProductCategoryResponse>>getWithPagination(Pageable pageable);

}
