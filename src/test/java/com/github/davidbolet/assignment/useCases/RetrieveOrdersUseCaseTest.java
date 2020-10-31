package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Order;
import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.OrderAlreadyExistsException;
import com.github.davidbolet.assignment.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveOrdersUseCaseTest {
    @Mock
    OrderRepository orderRepository;

    RetrieveOrdersUseCase retrieveOrdersUseCase;

    @Before
    public void setUp() {
        retrieveOrdersUseCase = new RetrieveOrdersUseCase(orderRepository);
    }

    @Test
    public void RetrieveOrdersShouldFindOrders() {
        LocalDateTime start = LocalDateTime.of(2020,Month.APRIL,1,0,0);
        LocalDateTime end = LocalDateTime.of(2020,Month.APRIL,30,23,59);
        Order o1 = getOrderWithIdAndTimePlaced(1,LocalDateTime.of(2020, Month.APRIL,2,20,0));
        Order o2 = getOrderWithIdAndTimePlaced(2,LocalDateTime.of(2020, Month.APRIL,23,20,0));
        List<Order> expected = Arrays.asList(o1,o2);
        given(orderRepository.findOrdersBetweenDates(start,end)).willReturn(expected);
        List<Order> result = retrieveOrdersUseCase.execute(new RetrieveOrdersUseCase.Request(start,end)).getOrders();
        assertThat(result).isEqualTo(expected);
    }

    @Test(expected = IllegalArgumentException.class)
    public void RetrieveOrdersShouldCheckParameters() {
        LocalDateTime start = LocalDateTime.of(2020, Month.APRIL, 1, 0, 0);
        List<Order> result = retrieveOrdersUseCase.execute(new RetrieveOrdersUseCase.Request(start, null)).getOrders();
    }

    private Order getOrderWithIdAndTimePlaced(Integer id, LocalDateTime placed) {
        Order o1 = new Order();
        o1.setId(id);
        o1.setBuyerEmail("test@test.es");
        Product p1 = new Product();
        p1.setCreationDate(placed);
        p1.setName("Product 1");
        p1.setSku("12344");
        p1.setPrice(BigDecimal.TEN);
        Product p2 = new Product();
        p2.setCreationDate(LocalDateTime.now());
        p2.setName("Product 2");
        p2.setSku("12345");
        p2.setPrice(BigDecimal.ONE);
        o1.getProducts().addAll(Arrays.asList(p1,p2));
        return o1;
    }
}