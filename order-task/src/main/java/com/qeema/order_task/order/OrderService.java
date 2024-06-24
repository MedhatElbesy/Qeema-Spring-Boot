package com.qeema.order_task.order;

import com.qeema.order_task.orderproduct.OrderProduct;
import com.qeema.order_task.orderproduct.OrderProductRepository;
import com.qeema.order_task.product.Product;
import com.qeema.order_task.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderProductRepository orderProductRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<OrderDetails> getAllOrdersWithProducts() {
        List<Order> orders = orderRepository.findAll();
        
        return orders.stream()
                .map(order -> {
                    List<OrderProduct> orderProducts = orderProductRepository.findByOrder(order);
                    List<Product> products = orderProducts.stream()
                            .map(OrderProduct::getProduct)
                            .collect(Collectors.toList());
                    
                    return new OrderDetails(order, products);
                })
                .collect(Collectors.toList());
    }
    
    @Transactional
    public Order createOrder(Long userId, List<OrderProductRequest> products){
        Order order = new Order();
        order.setUserId(userId);
        order = orderRepository.save(order);

        Set<Long> seenProductIds = new HashSet<>();

        for(OrderProductRequest productRequest : products){
            Long productId = productRequest.getProductId();

            Product product = productRepository.findById(productRequest.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

            if (product.getQuantity() < productRequest.getQuantity()) {
                throw new IllegalArgumentException("Insufficient product quantity");
            }

            if (!seenProductIds.add(productId)) {
                throw new IllegalArgumentException("Duplicate productId found: " + productId);
            }
            product.setQuantity(product.getQuantity() - productRequest.getQuantity());
            productRepository.save(product);

            OrderProduct orderProduct = new OrderProduct();
            orderProduct.setOrder(order);
            orderProduct.setProduct(product);
            orderProduct.setQuantity(productRequest.getQuantity());
            orderProduct.setPrice(product.getPrice() * productRequest.getQuantity());
            orderProductRepository.save(orderProduct);
        }
        return order;
    }
}
