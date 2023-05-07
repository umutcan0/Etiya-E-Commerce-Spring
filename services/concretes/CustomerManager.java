package com.etiya.ecommerce.services.concretes;

import com.etiya.ecommerce.core.exception.types.NotFoundException;
import com.etiya.ecommerce.core.internationalization.MessageService;
import com.etiya.ecommerce.core.utils.mapping.ModelMapperService;
import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.core.utils.result.SuccessDataResult;
import com.etiya.ecommerce.entities.concrete.Customer;
import com.etiya.ecommerce.repositories.abstracts.CustomerDao;
import com.etiya.ecommerce.services.abstracts.CustomerService;
import com.etiya.ecommerce.services.constans.Messages;
import com.etiya.ecommerce.services.dtos.requests.customer.AddCustomerRequest;
import com.etiya.ecommerce.services.dtos.responses.customer.AddCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.GetByIdCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.ListCustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;


@Service
@AllArgsConstructor
public class CustomerManager implements CustomerService {

    private CustomerDao customerDao;
    private ModelMapperService mapperService;
    private MessageService messageService;

    @Override
    public DataResult<List<ListCustomerResponse>> getAll() {
        List<Customer> customersList = customerDao.findAll();

        List<ListCustomerResponse> customerResponses = customersList.
                stream().
                map(customer -> {
                    ListCustomerResponse customerResponse = mapperService.forResponse().map(customer, ListCustomerResponse.class);
                    return customerResponse;
                }).collect(Collectors.toList());

        return new SuccessDataResult<>(customerResponses, messageService.getMessage(Messages.Customer.CustomerListed));
    }

    @Override
    public DataResult<GetByIdCustomerResponse> getById(Integer id) {

        Customer customer = customerDao.findById(id).orElseThrow(
                () -> new NotFoundException(messageService.getMessageWithParams(Messages.Customer.customerNotFound, id))
        );

        GetByIdCustomerResponse response = mapperService.forResponse().map(customer, GetByIdCustomerResponse.class);
        return new SuccessDataResult<>(response, messageService.getMessage(Messages.Customer.customerWithId));
    }

    @Override
    public DataResult<AddCustomerResponse> add(AddCustomerRequest addCustomerRequest) {
        Customer customer = new Customer();
        customer = mapperService.forRequest().map(addCustomerRequest, Customer.class);
        customerDao.save(customer);
        AddCustomerResponse addCustomerResponse = mapperService.forResponse().map(customer, AddCustomerResponse.class);
        return new SuccessDataResult<>(addCustomerResponse, messageService.getMessage(Messages.Customer.customerAdd));
    }

    protected Customer customerId(Integer customer) {
        Customer customer1 = customerDao.findById(customer).orElseThrow(
                () -> new NotFoundException(messageService.getMessageWithParams(Messages.Customer.customerNotFound, customer))
        );
        return customer1;
    }

    @Override
    public DataResult<Slice<ListCustomerResponse>> getWithPagination(Pageable pageable) {
        return new SuccessDataResult<>(customerDao.getAll(pageable));
    }
}
