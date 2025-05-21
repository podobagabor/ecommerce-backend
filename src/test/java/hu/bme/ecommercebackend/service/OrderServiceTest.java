package hu.bme.ecommercebackend.service;

import hu.bme.ecommercebackend.dto.Order.OrderCreateDto;
import hu.bme.ecommercebackend.dto.Order.OrderDto;
import hu.bme.ecommercebackend.dto.Product.ProductDto;
import hu.bme.ecommercebackend.model.*;
import hu.bme.ecommercebackend.model.enums.Gender;
import hu.bme.ecommercebackend.model.enums.OrderStatus;
import hu.bme.ecommercebackend.model.enums.Role;
import hu.bme.ecommercebackend.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private OrderService orderService;

    Order order1;
    Order order2;

    User mockUser;

    Product mockProduct1;
    Product mockProduct2;

    @Value("${admin.email}")
    private String adminEmail;

    @BeforeEach()
    void init() {
        Category mockCategory1 = new Category(1L, "Ruházat");
        Brand mockBrand1 = new Brand(1L, "Samsung", "image_url", "Technical devices from Korea");

        mockProduct1 = new Product(1L, "Test poduct1", 5, "Teszt description1", null, List.of(), 100, mockCategory1, mockBrand1);
        mockProduct2 = new Product(2L, "Test poduct2", 5, "Teszt description2", 10, List.of(), 100, mockCategory1, mockBrand1);

        mockUser = new User("asdf", Role.USER, "testEmail1@email.com", "Test1First", "Test1Last", "063010102", Set.of(mockProduct1), Gender.MALE, new ArrayList<>(), new ArrayList<>(), new Address("HU", "Dabas", "Fő utca", "23", "2371"));
        List<CartElement> cartElements = Arrays.asList(new CartElement(111L, mockProduct1, 3, mockUser), new CartElement(222L, mockProduct2, 2, mockUser));
        mockUser.getCart().addAll(cartElements);
        order1 = new Order();
        order2 = new Order();
        List<OrderItem> items1 = Arrays.asList(new OrderItem(111L, mockProduct1, 2, order1), new OrderItem(222L, mockProduct2, 2, order1));
        List<OrderItem> items2 = Arrays.asList(new OrderItem(333L, mockProduct1, 4, order2), new OrderItem(444L, mockProduct2, 6, order2));

        order1.setItems(items1);
        order1.setUser(mockUser);
        order1.setId(999L);
        order1.setShippingAddress(new Address("MO", "Bp", "Dózsa", "3/b", "2371"));
        order1.setBillingAddress(new Address("MO", "Bp", "Dózsa", "3/b", "2371"));
        order1.setDate(LocalDateTime.now());

        order2.setItems(items2);
        order2.setUser(mockUser);
        order2.setId(888L);
        order2.setShippingAddress(new Address("MO", "Bp", "Dózsa", "3/b", "2371"));
        order2.setBillingAddress(new Address("MO", "Bp", "Dózsa", "3/b", "2371"));
        order2.setDate(LocalDateTime.now());

    }

    @Test
    void createOrderTest() {
        Order order = new Order(mockUser, new ArrayList<>(), order1.getBillingAddress(), order1.getShippingAddress());
        List<OrderItem> items = Arrays.asList(new OrderItem(mockProduct1, 3, order), new OrderItem(mockProduct2, 2, order));
        order.setItems(items);
        OrderDto orderDto = new OrderDto(order);


        OrderCreateDto mockOrderCreat = new OrderCreateDto(order1.getBillingAddress(), order1.getShippingAddress());
        when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);
        when(productService.reduceCount(mockProduct1.getId(), 3)).thenReturn(new ProductDto(mockProduct1));
        when(productService.reduceCount(mockProduct2.getId(), 2)).thenReturn(new ProductDto(mockProduct2));
        when(emailService.getNewOrderMessageForAdmin(any(Order.class))).thenReturn("Test admin email text");
        when(emailService.orderCreatedMessage(eq(mockUser.getFirstName() + " " + mockUser.getLastName()), any(Order.class))).thenReturn("Test user email text");
        doNothing().when(emailService).sendEmail(mockUser.getEmail(), "Order successfully created", "Test user email text");
        doNothing().when(emailService).sendEmail(adminEmail, "New order", "Test admin email text");
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        OrderDto currentOrder = orderService.createOrder(mockUser.getId(), mockOrderCreat);

        assertEquals(orderDto.getItems(), currentOrder.getItems());
        assertEquals(orderDto.getBillingAddress(), currentOrder.getBillingAddress());
        assertEquals(orderDto.getShippingAddress(), currentOrder.getShippingAddress());
        assertEquals(orderDto.getStatus(), currentOrder.getStatus());
    }

    @Test
    void getOrderDtoByIdTest() {
        when(orderRepository.findById(order1.getId())).thenReturn(Optional.ofNullable(order1));

        OrderDto currentOrder = orderService.getOrderDtoById(order1.getId());

        assertEquals(new OrderDto(order1), currentOrder);

    }

    @Test
    void getAllOrdersTest() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<OrderDto> currentOrders = orderService.getAllOrders();

        assertEquals(Arrays.asList(new OrderDto(order1), new OrderDto(order2)), currentOrders);
    }

    @Test
    void getOrdersOfUserTest() {
        mockUser.getOrders().addAll(Arrays.asList(order1, order2));

        when(userService.getUserById(mockUser.getId())).thenReturn(mockUser);
        List<OrderDto> currentOrders = orderService.getOrdersOfUser(mockUser.getId());

        assertEquals(Arrays.asList(new OrderDto(order1), new OrderDto(order2)), currentOrders);
    }

    @Test
    void changeOrderStatusTest() {
        when(orderRepository.findById(order1.getId())).thenReturn(Optional.ofNullable(order1));

        OrderDto currentOrderDto = orderService.changeOrderStatus(order1.getId(), OrderStatus.IN_PROGRESS);
        order1.setStatus(OrderStatus.IN_PROGRESS);

        assertEquals(new OrderDto(order1), currentOrderDto);

    }

    @Test
    void deleteOrderByIdTest() {
        doNothing().when(orderRepository).deleteById(order1.getId());
        Boolean result = orderService.deleteOrderById(order1.getId());
        assertTrue(result);
    }
}