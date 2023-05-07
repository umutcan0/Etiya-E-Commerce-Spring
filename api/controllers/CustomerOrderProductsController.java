package com.etiya.ecommerce.api.controllers;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.services.concretes.CustomerOrderProductManager;
import com.etiya.ecommerce.services.dtos.requests.customerOrderProduct.AddCustomerOrderProductRequest;
import com.etiya.ecommerce.services.dtos.requests.customerOrderProduct.DeleteCustomerOrderProductRequest;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.AddCustomerOrderProductResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.DeleteCustomerOrderProductResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.ListCustomerOrderProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer_product")
@AllArgsConstructor
public class CustomerOrderProductsController {

    private final CustomerOrderProductManager manager;

    @GetMapping("")
    public DataResult<List<ListCustomerOrderProductResponse>> getAll() {
        return manager.getAll();
    }

    @PostMapping("")
    public DataResult<AddCustomerOrderProductResponse> addCustomer(AddCustomerOrderProductRequest request) {
        return manager.addCustomerOrderProduct(request);
    }
    @GetMapping("page")
    public DataResult<Slice<ListCustomerOrderProductResponse>>getWithPage(@RequestParam int page,
                                                                          @RequestParam int size){
        Pageable pageable= PageRequest.of(page,size);
        return manager.getWithPagination(pageable);
    }
    @DeleteMapping("{id}")
    public DataResult<DeleteCustomerOrderProductResponse>removeCustomerOrderProduct(@PathVariable DeleteCustomerOrderProductRequest request){
        DeleteCustomerOrderProductResponse response= manager.deleteCustomerOrderProduct(request).getData();
        return new SuccessDataResult<>(response);
    }
}
