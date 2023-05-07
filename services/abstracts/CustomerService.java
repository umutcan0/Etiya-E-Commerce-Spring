package com.etiya.ecommerce.services.abstracts;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.services.dtos.requests.customer.AddCustomerRequest;
import com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.AddCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.GetByIdCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.ListCustomerResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CustomerService {

    DataResult<List<ListCustomerResponse>> getAll();

    DataResult<GetByIdCustomerResponse> getById(Integer id);

    DataResult<AddCustomerResponse> add(AddCustomerRequest addCustomerRequest);
    DataResult<Slice<ListCustomerResponse>>getWithPagination(Pageable pageable);



}
