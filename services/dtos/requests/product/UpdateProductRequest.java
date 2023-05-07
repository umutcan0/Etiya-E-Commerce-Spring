package com.etiya.ecommerce.services.dtos.requests.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateProductRequest {



    @NotBlank(message = "Price alanı boş bırakılamaz.")
    @Size(min = 5, message = "Price alanı 2 karakterden kısa olamaz.")
    private String price;

    @NotBlank(message = "Details alanı boş bırakılamaz.")
    @Size(min = 20, message = "Detail alanı 20 karakterden kısa olamaz.")
    private String details;

    @NotBlank(message = "Name alanı boş bırakılamaz.")
    @Size(min = 6, message = "Name alanı 6 karakterden kısa olamaz.")
    private String name;

    private int categoryId;

}
