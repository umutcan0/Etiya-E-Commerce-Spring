package com.etiya.ecommerce.repositories.abstracts;

import com.etiya.ecommerce.core.utils.result.DataResult;
import com.etiya.ecommerce.entities.concrete.ProductCategory;
import com.etiya.ecommerce.services.dtos.responses.customer.ListCustomerResponse;
import com.etiya.ecommerce.services.dtos.responses.productCategory.ListProductCategoryResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductCategoryDao extends JpaRepository<ProductCategory, Integer> {

    @Query("SELECT pc FROM ProductCategory pc")
    List<ProductCategory> findAllByProductCategory();

    @Query("SELECT pc.id FROM ProductCategory pc")
    List<ProductCategory> findAllByProductCategoryById();

    @Query("SELECT COUNT(pc.product) FROM ProductCategory pc")
    long countProductCategoryProduct();

    @Query("SELECT DISTINCT pc.name FROM ProductCategory pc")
    List<ProductCategory> differentCity();

    @Query("SELECT pc.name,pc.id,p.name FROM ProductCategory pc JOIN pc.product p")
    List<ProductCategory> getProductByProductCategory();

    @Query(value = "select new com.etiya.ecommerce.services.dtos.responses.address.ListAddressResponse(a.id,a.address,a.city,a.country) from Address a")
    Slice<ListProductCategoryResponse> getAll(Pageable pageable);

}
