package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Order.OrderCreateDto;
import hu.bme.ecommercebackend.dto.Order.OrderDto;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import hu.bme.ecommercebackend.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateDto order, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(jwt.getSubject(), order));
    }

    @GetMapping("/user")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(orderService.getOrdersOfUser(jwt.getSubject()));
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrder(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PutMapping("/modify/{id}/{status}")
    public ResponseEntity<OrderDto> changeOrderStatus(@PathVariable Long id, @PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.changeOrderStatus(id, status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderDtoById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteOrderById(id));
    }
}
