package com.etiya.ecommerce.api.controllers;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.services.abstracts.CustomerOrderService;
import com.etiya.ecommerce.services.concretes.CustomerOrderManager;
import com.etiya.ecommerce.services.dtos.requests.customerOrder.AddCustomerOrderRequest;
import com.etiya.ecommerce.services.dtos.responses.customerOrder.AddCustomerOrderResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrder.GetByIdCustomerOrderResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrder.ListCustomerOrderResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.DeleteCustomerOrderProductResponse;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customerOrders")
@AllArgsConstructor
public class CustomerOrdersController {

    private CustomerOrderService customerOrderService;

    @GetMapping("")
    public DataResult<List<ListCustomerOrderResponse>> getAll() {

        return customerOrderService.getAll();
    }

    @GetMapping("getWithPagination")
    public DataResult<Slice<ListCustomerOrderResponse>> getAll(@RequestParam int page, @RequestParam int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        return customerOrderService.getWithPagination(pageable);
    }

    @GetMapping("/{id}")
    public DataResult<GetByIdCustomerOrderResponse> getById(@PathVariable int id) {

        return customerOrderService.getById(id);
    }

    @PostMapping("/save")
    public DataResult<AddCustomerOrderResponse> add(@RequestBody AddCustomerOrderRequest addCustomerOrderRequest) {
        return customerOrderService.add(addCustomerOrderRequest);
    }

}
