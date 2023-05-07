package com.etiya.ecommerce.services.dtos.responses.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProductResponse {

    private int id;
    private String price;
    private String details;
    private String name;

}
