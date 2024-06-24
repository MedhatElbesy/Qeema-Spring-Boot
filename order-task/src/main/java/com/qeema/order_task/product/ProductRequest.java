package com.qeema.order_task.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {

    @NotNull(message = "Name is mandatory")
    private String name;

    @DecimalMin(value = "0.01", message = "Price must be greater than or equal to 0.01")
    private double price;

    @Min(value = 1, message = "Quantity must be greater than or equal to 1")
    private int quantity;
}

