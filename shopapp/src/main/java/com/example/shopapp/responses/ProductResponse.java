package com.example.shopapp.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends BaseResponse{
    @JsonProperty("product_name")
    private String productName;
    private Float price;
    @JsonProperty("url_image")
    private String urlImage;
    private String description;
    @JsonProperty("category_id")
    private Long categoryId;
}
