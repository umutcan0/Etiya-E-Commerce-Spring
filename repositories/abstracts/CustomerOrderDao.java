package com.etiya.ecommerce.repositories.abstracts;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.entities.concrete.CustomerOrder;
import com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.customer.ListCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrder.ListCustomerOrderResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.ListCustomerOrderProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderDao extends JpaRepository<CustomerOrder, Integer> {

    @Query("SELECT co FROM CustomerOrder co")
    List<CustomerOrder> findAllByCustomerOrder();

    @Query("SELECT co.customerOrderId FROM CustomerOrder co")
    List<CustomerOrder> findAllByCustomerOrderById();

    @Query("SELECT co.price FROM CustomerOrder co")
    List<CustomerOrder> findAllByCustomerOrderByPrice();

    @Query("SELECT c.fullName, c.phone, c.email, c.details  FROM CustomerOrder co " +
            "JOIN co.customer c WHERE c.id = :customerId")
    List<CustomerOrder> getCustomerOrderByCustomers(int customerId);





    @Query(value = "select new com.etiya.ecommerce.services.dtos.responses.customerOrder.ListCustomerOrderResponse" +
            "(co.customerOrderId,co.statusCode,co.placed,co.paid,co.price,co.details,co.customer.fullName) " +
            "from CustomerOrder co")
    Slice<ListCustomerOrderResponse> getAll(Pageable pageable);

}
