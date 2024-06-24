package com.qeema.order_task.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    
    private final OrderService orderService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderDetails>> getAllOrdersWithProducts() {
        List<OrderDetails> orders = orderService.getAllOrdersWithProducts();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')") 
    @PostMapping
    public ResponseEntity<Order> createOrder(
        @RequestParam @NotNull(message = "User ID is mandatory") Long userId,
        @RequestBody @Valid List<OrderProductRequest> products
    ){
        Order order = orderService.createOrder(userId, products);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
