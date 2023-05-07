package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.CustomerOrderProduct;
import com.etiya.ecommerce.repositories.abstracts.ICustomerOrderProductDao;
import com.etiya.ecommerce.services.abstracts.CustomerOrderProductService;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.customerOrderProduct.AddCustomerOrderProductRequest;
import com.etiya.ecommerce.services.dtos.requests.customerOrderProduct.DeleteCustomerOrderProductRequest;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.AddCustomerOrderProductResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.DeleteCustomerOrderProductResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.ListCustomerOrderProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerOrderProductManager implements CustomerOrderProductService {

    private final ICustomerOrderProductDao customerOrderProductDao;
    private final ProductManager productManager;
    private ModelMapperService mapperService;
    private final CustomerOrderManager customerOrderManager;
    private final MessageService messageService;


    @Override
    public DataResult<List<ListCustomerOrderProductResponse>> getAll() {
        List<ListCustomerOrderProductResponse> responses = customerOrderProductDao.getAll();
        return new SuccessDataResult<>(responses,messageService.getMessage(Messages.CustomerOrderProduct.customerOrderProductListed));
    }

    @Override
    public DataResult<AddCustomerOrderProductResponse> addCustomerOrderProduct(AddCustomerOrderProductRequest addCustomerOrderProductRequest) {

        checkIfCustomerOrderWithIdExists(addCustomerOrderProductRequest.getOrderId());
        checkIfCustomerProductWithIdExists(addCustomerOrderProductRequest.getProductId());

        CustomerOrderProduct customerOrderProduct = mapperService.forResponse()
                .map(addCustomerOrderProductRequest, CustomerOrderProduct.class);

        customerOrderProductDao.save(customerOrderProduct);

        AddCustomerOrderProductResponse customerOrderProductResponse = mapperService.forResponse().map(customerOrderProduct, AddCustomerOrderProductResponse.class);
        return new SuccessDataResult<>(customerOrderProductResponse,messageService.getMessage(Messages.CustomerOrderProduct.customerOrderProductAdd));
    }

    private void checkIfCustomerOrderWithIdExists(int id){
        customerOrderManager.getById(id);
    }
    private void checkIfCustomerProductWithIdExists(int id){
        productManager.getById(id);
    }

    @Override
    public DataResult<Slice<ListCustomerOrderProductResponse>> getWithPagination(Pageable pageable) {
        return new SuccessDataResult<>(customerOrderProductDao.getAll(pageable), messageService.getMessage(Messages.CustomerOrderProduct.customerOrderProductListed));
    }

    @Override
    public DataResult<DeleteCustomerOrderProductResponse>deleteCustomerOrderProduct(DeleteCustomerOrderProductRequest request) {
        CustomerOrderProduct orderId=customerOrderProductDao.findById(request.getId()).orElseThrow(()->
                new NotFoundException(messageService.getMessageWithParams(Messages.CustomerOrderProduct.customerOrderProductNotFound,request.getId())));

         DeleteCustomerOrderProductResponse response=mapperService.forResponse().map(orderId,DeleteCustomerOrderProductResponse.class);
         return new SuccessDataResult<>(response, messageService.getMessage(Messages.CustomerOrderProduct.customerOrderProductDelete));
    }
}
