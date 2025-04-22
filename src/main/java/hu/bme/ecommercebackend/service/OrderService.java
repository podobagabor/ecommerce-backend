package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Order.OrderCreateDto;
import hu.bme.ecommercebackend.dto.Order.OrderDto;
import hu.bme.ecommercebackend.model.*;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import hu.bme.ecommercebackend.repository.CartRepository;
import hu.bme.ecommercebackend.repository.OrderItemRepository;
import hu.bme.ecommercebackend.repository.OrderRepository;
import hu.bme.ecommercebackend.repository.UserRepository;
import hu.bme.ecommercebackend.specification.OrderSpecification;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final EmailService emailService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserService userService, UserRepository userRepository,
                        CartRepository cartRepository, EmailService emailService) {
        this.orderItemRepository = orderItemRepository;
        this.orderRepository = orderRepository;
        this.userService = userService;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.emailService = emailService;
    }

    @Transactional
    public OrderDto createOrder(String userId, OrderCreateDto orderCreateDto) {
        User userEntity = userService.getUserById(userId);
        Order order = new Order(userEntity, new ArrayList<>(), orderCreateDto.getBillingAddress(), orderCreateDto.getShippingAddress());
        List<OrderItem> orderItems = userEntity.getCart().stream().map(cartElement -> new OrderItem(cartElement, order)).toList();
        userEntity.getCart().forEach(cartElement ->
                cartElement.getProduct().setCount(cartElement.getProduct().getCount() - cartElement.getQuantity())
        );
        order.setItems(orderItems);
        userEntity.getCart().clear();
        userRepository.save(userEntity);
        emailService.sendEmail(userEntity.getEmail(), "Order successfully created", emailService.orderCreatedMessage(userEntity.getFirstName() + " " + userEntity.getLastName(), order));
        return new OrderDto(orderRepository.save(order));
    }

    public OrderDto getOrderDtoById(Long id) {
        return new OrderDto(orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Unknown entity")));
    }

    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public Page<OrderDto> getOrderListPage(OrderStatus status, Long id, Pageable pageable) {
        Specification<Order> spec = OrderSpecification.filterBy(id,status);
        return orderRepository.findAll(spec,pageable).map(OrderDto::new);
    }

    public List<OrderDto> getOrdersOfUser(String id) {
        User userEntity = userService.getUserById(id);
        return userEntity.getOrders().stream().map(OrderDto::new).collect(Collectors.toList());
    }

    public OrderDto changeOrderStatus(Long orderId, OrderStatus status) {
        Order orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Unknown entity"));
        orderEntity.setStatus(status);
        emailService.sendEmail(orderEntity.getUser().getEmail(),"Order status changed",emailService.orderStatusChangedMessage(orderEntity.getUser().getFirstName() + " " + orderEntity.getUser().getLastName(),orderEntity));
        return new OrderDto(orderRepository.save(orderEntity));
    }

    public Boolean deleteOrderById(Long id) {
        orderRepository.deleteById(id);
        return true;
    }
}
