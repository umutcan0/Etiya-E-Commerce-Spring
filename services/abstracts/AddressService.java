package com.etiya.ecommerce.services.abstracts;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Address;
import com.etiya.ecommerce.services.dtos.requests.address.AddAddressRequest;
import com.etiya.ecommerce.services.dtos.responses.address.AddAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.address.GetByIdAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;


import java.util.List;

public interface AddressService {

    DataResult<AddAddressResponse> addAddress(AddAddressRequest addAddressRequest);

    DataResult<GetByIdAddressResponse> addressGetById(Integer id);

    DataResult<List<ListAddressResponse>> getAll();
    DataResult<Slice<ListAddressResponse>>getWithPagination(Pageable pageable);



}
