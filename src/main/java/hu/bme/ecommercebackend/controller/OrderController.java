package hu.bme.ecommercebackend.controller;

import hu.bme.ecommercebackend.dto.Order.OrderCreateDto;
import hu.bme.ecommercebackend.dto.Order.OrderDto;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import hu.bme.ecommercebackend.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderCreateDto order, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(jwt.getSubject(), order));
    }

    @GetMapping(value = "/user")
    public ResponseEntity<List<OrderDto>> getOrderOfUser(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(orderService.getOrdersOfUser(jwt.getSubject()));
    }

    @GetMapping()
    public ResponseEntity<List<OrderDto>> getAllOrder(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping(value = "/list")
    public ResponseEntity<Page<OrderDto>> getAllOrder(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) Long id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortId,
            @RequestParam(required = false) Sort.Direction sortDirection
    ) {
        Pageable pageable = PageRequest.of(page, size, sortDirection == null ? Sort.Direction.ASC : sortDirection, sortId);
        return ResponseEntity.ok(orderService.getOrderListPage(status, id, pageable));
    }

    @PutMapping(value = "/modify/{id}/{status}")
    public ResponseEntity<OrderDto> changeOrderStatus(@PathVariable Long id, @PathVariable OrderStatus status) {
        return ResponseEntity.ok(orderService.changeOrderStatus(id, status));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDto> getOrderDtoById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderDtoById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.deleteOrderById(id));
    }
}
