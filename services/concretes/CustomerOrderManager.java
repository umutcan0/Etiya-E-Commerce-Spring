package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Customer;
import com.etiya.ecommerce.repositories.abstracts.CustomerOrderDao;
import com.etiya.ecommerce.services.abstracts.CustomerOrderService;
import com.etiya.ecommerce.entities.concrete.CustomerOrder;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.customerOrder.AddCustomerOrderRequest;
import com.etiya.ecommerce.services.dtos.responses.customerOrder.AddCustomerOrderResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrder.GetByIdCustomerOrderResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrder.ListCustomerOrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerOrderManager implements CustomerOrderService {

    private CustomerOrderDao customerOrderDao;
    private ModelMapperService modelMapperService;
    private CustomerManager customerManager;
    private MessageService messageService;

    @Override
    public DataResult<AddCustomerOrderResponse> add(AddCustomerOrderRequest addCustomerOrderRequest) {

         checkIfCustomerWithIdExists(addCustomerOrderRequest.getCustomerId());
        CustomerOrder customerOrder = modelMapperService.forRequest().map(addCustomerOrderRequest, CustomerOrder.class);

        customerOrderDao.save(customerOrder);

        AddCustomerOrderResponse response = modelMapperService.forResponse().map(customerOrder, AddCustomerOrderResponse.class);

        return new SuccessDataResult<>(response, messageService.getMessage(Messages.CustomerOrder.customerOrderAdd));
    }
    private void checkIfCustomerWithIdExists(int id){
        customerManager.getById(id);
    }

/*
        CustomerOrder customerOrder=new CustomerOrder();
        customerOrder.setCustomer(customer);
        customerOrder.setPlaced(addCustomerOrderRequest.getPlaced());
        customerOrder.setDetails(addCustomerOrderRequest.getDetails());
        customerOrder.setStatusCode(addCustomerOrderRequest.getStatusCode());
        customerOrder.setPaid(addCustomerOrderRequest.getPaid());
        customerOrder.setPrice(addCustomerOrderRequest.getPrice());

        customerOrderDao.save(customerOrder);

        AddCustomerOrderResponse response=new AddCustomerOrderResponse();


        response.setId(customerOrder.getId());
        response.setPaid(customerOrder.getPaid());
        response.setPrice(customerOrder.getPrice());
        response.setPlaced(customerOrder.getPlaced());
        response.setStatusCode(customerOrder.getStatusCode());
        response.setCustomerId(customerOrder.getCustomer().getId());
        response.setFullName(customerOrder.getCustomer().getFullName());


        return response;


 */


    @Override
    public DataResult<List<ListCustomerOrderResponse>> getAll() {
        List<CustomerOrder> customerOrder = customerOrderDao.findAll();

        List<ListCustomerOrderResponse> responses = customerOrder.stream().map(customerOrder1 -> {
            ListCustomerOrderResponse customerOrder2 = modelMapperService.forResponse().
                    map(customerOrder1, ListCustomerOrderResponse.class);
            return customerOrder2;
        }).collect(Collectors.toList());

        return new SuccessDataResult<>(responses, messageService.getMessage(Messages.CustomerOrder.customerOrderListed));
    }

    @Override
    public DataResult<GetByIdCustomerOrderResponse> getById(int id) {
        CustomerOrder customerOrder = customerOrderDao.findById(id)
                .orElseThrow(() -> new NotFoundException
                        (messageService.getMessageWithParams(Messages.CustomerOrder.customerOrderNotFound,id)));
        GetByIdCustomerOrderResponse response=modelMapperService.forResponse().map(customerOrder,GetByIdCustomerOrderResponse.class);
        return new SuccessDataResult<>(response, messageService.getMessage(Messages.CustomerOrder.customerOrderWithId));
        }


    protected CustomerOrder customerOrderId(Integer id) {
        CustomerOrder customerOrder = customerOrderDao.findById(id).
                orElseThrow(()->new NotFoundException(messageService.getMessageWithParams
                        (Messages.CustomerOrder.customerOrderNotFound,id)));
        return customerOrder;
    }

    @Override
    public DataResult<Slice<ListCustomerOrderResponse>> getWithPagination(Pageable pageable) {
        return new SuccessDataResult<>(customerOrderDao.getAll(pageable), messageService.getMessage(Messages.CustomerOrder.customerOrderListed));
    }
}


