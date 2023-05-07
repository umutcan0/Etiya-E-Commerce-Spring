package com.etiya.ecommerce.services.dtos.responses.productCategory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductCategoryResponse {

    private int productCategoryId;
    private String name;
}
