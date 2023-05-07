package com.etiya.ecommerce.services.dtos.responses.customerOrder;

import com.etiya.ecommerce.entities.concrete.Customer;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListCustomerOrderResponse {
    private int customerOrderId;
    private String statusCode;
    private String placed;
    private String paid;
    private String price;
    private String details;
    private String customerName;
}
