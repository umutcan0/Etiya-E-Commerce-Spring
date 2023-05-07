package com.etiya.ecommerce.services.dtos.requests.productCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductCategoryRequest {


    @NotBlank(message = "Name alanı boş bırakılamaz !")
    @NotNull(message = "Name alanı boş bırakılamaz !")
    @Size(min = 2,message = "Name alanı en az 4 karakter olmalı")
    private String name;

}
