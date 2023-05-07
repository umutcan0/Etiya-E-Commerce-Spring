package com.etiya.ecommerce.services.dtos.responses.customerOrderProduct;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddCustomerOrderProductResponse{

    private int id;

    private int quantity;

    private int orderId;

    private int productId;
}
