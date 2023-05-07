package com.etiya.ecommerce.services.dtos.responses.customerOrderProduct;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ListCustomerOrderProductResponse {

    private int id;
    private int orderId;
    private int productId;

}
