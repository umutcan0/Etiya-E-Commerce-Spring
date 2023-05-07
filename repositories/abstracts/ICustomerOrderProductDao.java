package com.etiya.ecommerce.repositories.abstracts;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.entities.concrete.CustomerOrderProduct;
import com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse;
import com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.ListCustomerOrderProductResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICustomerOrderProductDao extends JpaRepository<CustomerOrderProduct, Integer> {

    @Query("SELECT cop FROM CustomerOrderProduct cop")
    List<CustomerOrderProduct> findAllByCustomerOrderProduct();

    @Query("SELECT cop.id FROM CustomerOrderProduct cop")
    List<CustomerOrderProduct> findAllByCustomerOrderProductById();

    @Query("SELECT cop.order FROM CustomerOrderProduct cop")
    List<CustomerOrderProduct> findAllByCustomerOrderProductByOrder();

    @Query("SELECT cop.product FROM CustomerOrderProduct cop")
    List<CustomerOrderProduct> findAllByCustomerOrderProductByProduct();

    @Query("SELECT COUNT(cop.comments) FROM CustomerOrderProduct cop")
    long countCustomerOrderProductComments();

    @Query("SELECT p.name, p.price  FROM CustomerOrderProduct cop " +
            "JOIN cop.order o " +
            "JOIN cop.product p WHERE p.id = :productId")
    List<CustomerOrderProduct> getCustomerOrderProductByOrdersAndProducts(int productId);

    @Query(value = "select new com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.ListCustomerOrderProductResponse (a.id,a.order.customerOrderId,a.product.id) from CustomerOrderProduct a")
    Slice<ListCustomerOrderProductResponse>getAll(Pageable pageable);

    @Query(value = "select new com.etiya.ecommerce.services.dtos.responses.customerOrderProduct.ListCustomerOrderProductResponse (a.id,a.order.customerOrderId,a.product.id) from CustomerOrderProduct a")
    List<ListCustomerOrderProductResponse>getAll();


}
