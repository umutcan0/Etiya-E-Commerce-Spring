package com.etiya.ecommerce.services.abstracts;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.Result;
import com.etiya.ecommerce.services.dtos.requests.customerOrderProduct.AddCustomerOrderProductRequest;
import com.etiya.ecommerce.services.dtos.requests.customerOrderProduct.DeleteCustomerOrderProductRequest;
import com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.AddCustomerOrderProductResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.DeleteCustomerOrderProductResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.ListCustomerOrderProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomerOrderProductService {

    DataResult<List<ListCustomerOrderProductResponse>> getAll();

    DataResult<AddCustomerOrderProductResponse> addCustomerOrderProduct(AddCustomerOrderProductRequest addCustomerOrderProductRequest);

    DataResult<Slice<ListCustomerOrderProductResponse>>getWithPagination(Pageable pageable);

   DataResult<DeleteCustomerOrderProductResponse> deleteCustomerOrderProduct(DeleteCustomerOrderProductRequest request);

}
