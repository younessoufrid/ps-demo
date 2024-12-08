package v1;

import com.demo.portailsaisie.backend.core.domain.Order;
import com.demo.portailsaisie.backend.core.mapping.OrderMapper;
import com.demo.portailsaisie.backend.core.repository.OrderRepository;
import com.demo.portailsaisie.backend.core.service.interfaces.OrderService;
import com.demo.portailsaisie.backend.core.service.v1.OrderServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import v1.config.TestConfiguration;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = TestConfiguration.class)
public class OrderServiceTest {

    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    private final String EXISTING_ORDER_NUMBER = "order_num_1";
    private final String NON_EXISTING_ORDER_NUMBER = "order_num_2";
    private final Long ID = 1000L;


    @BeforeAll
    public void init() {
        orderService = new OrderServiceImpl(orderRepository, orderMapper);

        Order existingOrder = Order.builder()
                .id(ID)
                .reference(EXISTING_ORDER_NUMBER)
                .build();

        when(orderRepository.findByNumber(EXISTING_ORDER_NUMBER))
                .thenReturn(Optional.of(existingOrder));
        when(orderRepository.findByNumber(NON_EXISTING_ORDER_NUMBER))
                .thenReturn(Optional.empty());
        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void saveIfNotExistWithOrderExists() {
        Order order = Order.builder()
                .reference(EXISTING_ORDER_NUMBER)
                .build();
        Order savedOrder = orderService.saveIfNotExist(order);

        assertEquals(order.getNumber(), savedOrder.getNumber());
    }

    @Test
    void saveIfNotExistWithOrderNotExists() {
        Order order = Order.builder()
                .reference(NON_EXISTING_ORDER_NUMBER)
                .build();
        Order savedOrder = orderService.saveIfNotExist(order);

        assertEquals(order, savedOrder);
    }

}
