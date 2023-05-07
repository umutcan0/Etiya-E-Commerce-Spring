package com.etiya.ecommerce.services.dtos.requests.customerOrderProduct;

import com.etiya.ecommerce.entities.concrete.CustomerOrder;
import com.etiya.ecommerce.entities.concrete.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddCustomerOrderProductRequest {

    private int quantity;

    private String comments;

    private int orderId;

    private int productId;

}
