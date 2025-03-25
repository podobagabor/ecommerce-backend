package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Order.OrderCreateDto;
import hu.bme.ecommercebackend.dto.Order.OrderDto;
import hu.bme.ecommercebackend.model.*;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import hu.bme.ecommercebackend.repository.CartRepository;
import hu.bme.ecommercebackend.repository.OrderItemRepository;
import hu.bme.ecommercebackend.repository.OrderRepository;
import hu.bme.ecommercebackend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserService userService, UserRepository userRepository,
                        CartRepository cartRepository) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
    }

    public OrderDto createOrder(String userId, OrderCreateDto orderCreateDto) {
        User userEntity = userService.getUserById(userId);
        Order order = new Order(userEntity,new ArrayList<>(),orderCreateDto.getBillingAddress(),orderCreateDto.getShippingAddress());
        List<OrderItem> orderItems = userEntity.getCart().stream().map(cartElement -> new OrderItem(cartElement, order)).toList();
        order.setItems(orderItems);
        userEntity.getCart().clear();
        userRepository.save(userEntity);
        return new OrderDto(orderRepository.save(order));
    }

    public OrderDto getOrderDtoById(Long id) {
        return new OrderDto(orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity")));
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public List<OrderDto> getOrdersOfUser(String id) {
        User userEntity = userService.getUserById(id);
        return userEntity.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, OrderStatus status) {
        Order orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        orderEntity.setStatus(status);
        return new OrderDto(orderRepository.save(orderEntity));
    }

    public Boolean deleteOrderById(Long id) {
        orderRepository.deleteById(id);
        return  true;
    }
}
