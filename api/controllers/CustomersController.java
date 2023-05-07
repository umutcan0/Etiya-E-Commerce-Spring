package com.etiya.ecommerce.api.controllers;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.services.abstracts.CustomerService;
import com.etiya.ecommerce.services.concretes.CustomerManager;
import com.etiya.ecommerce.services.dtos.requests.customer.AddCustomerRequest;
import com.etiya.ecommerce.services.dtos.responses.customer.AddCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.GetByIdCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.ListCustomerResponse;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@AllArgsConstructor
public class CustomersController {

    private CustomerService customerService;

    @GetMapping("")
    public DataResult<List<ListCustomerResponse>> getAll() {
        return customerService.getAll();
    }


    @GetMapping("getWithPagination")
    public DataResult<Slice<ListCustomerResponse>> getAll(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize)
    {
        Pageable pageable = PageRequest.of(page, pageSize);
        return customerService.getWithPagination(pageable);
    }

    @GetMapping("/{id}")
    public DataResult<GetByIdCustomerResponse> getById(@PathVariable int id) {
        return customerService.getById(id);
    }

    @PostMapping("")
    public DataResult<AddCustomerResponse> add(@Valid @RequestBody AddCustomerRequest request) {
        return customerService.add(request);
    }


}
