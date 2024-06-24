package com.qeema.order_task.order;

import com.qeema.order_task.product.Product;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetails {

    private Order order;
    private List<Product> products;

    public OrderDetails(Order order, List<Product> products) {
        this.order = order;
        this.products = products;
    }
}

