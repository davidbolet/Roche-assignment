package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Order;
import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.MissingRequiredFieldsException;
import com.github.davidbolet.assignment.exception.OrderAlreadyExistsException;
import com.github.davidbolet.assignment.exception.ProductNotFoundException;
import com.github.davidbolet.assignment.repository.OrderRepository;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PlaceOrderUseCaseTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    ProductRepository productRepository;

    PlaceOrderUseCase placeOrderUseCase;

    @Before
    public void setUp() {
        placeOrderUseCase = new PlaceOrderUseCase(orderRepository, productRepository);
    }

    @Test
    public void PlaceOrderShouldCreateOrder() {
        Order o1 = new Order();
        o1.setId(1);
        o1.setBuyerEmail("test@test.es");
        Product p1 = new Product();
        p1.setCreationDate(LocalDateTime.now());
        p1.setName("Product 1");
        p1.setSku("12344");
        p1.setPrice(BigDecimal.TEN);
        Product p2 = new Product();
        p2.setCreationDate(LocalDateTime.now());
        p2.setName("Product 2");
        p2.setSku("12345");
        p2.setPrice(BigDecimal.ONE);
        o1.getProducts().addAll(Arrays.asList(p1,p2));
        given(productRepository.findAllById(Arrays.asList(p1.getSku(),p2.getSku()))).willReturn(Arrays.asList(p1,p2));
        given(orderRepository.existsById(o1.getId())).willReturn(false);
        given(orderRepository.save(o1)).willReturn(o1);
        PlaceOrderUseCase.Response response = placeOrderUseCase.execute(new PlaceOrderUseCase.Request(o1));
        assertThat(response.getOrder()).isEqualTo(o1);
    }


    @Test(expected = OrderAlreadyExistsException.class)
    public void PlaceOrderShouldCheckIfOrderIdExists() {
        Order o1 = new Order();
        o1.setId(1);
        o1.setBuyerEmail("test@test.es");
        Product p1 = new Product();
        p1.setCreationDate(LocalDateTime.now());
        p1.setName("Product 1");
        p1.setSku("12344");
        p1.setPrice(BigDecimal.TEN);
        Product p2 = new Product();
        p2.setCreationDate(LocalDateTime.now());
        p2.setName("Product 2");
        p2.setSku("12345");
        p2.setPrice(BigDecimal.ONE);
        o1.getProducts().addAll(Arrays.asList(p1,p2));
        given(orderRepository.existsById(o1.getId())).willReturn(true);
        PlaceOrderUseCase.Response response = placeOrderUseCase.execute(new PlaceOrderUseCase.Request(o1));
    }

    @Test(expected = ProductNotFoundException.class)
    public void PlaceOrderShouldComplainIfAProductNotExists() {
        Order o1 = new Order();
        o1.setId(1);
        o1.setBuyerEmail("test@test.es");
        Product p1 = new Product();
        p1.setCreationDate(LocalDateTime.now());
        p1.setName("Product 1");
        p1.setSku("12344");
        p1.setPrice(BigDecimal.TEN);
        Product p2 = new Product();
        p2.setCreationDate(LocalDateTime.now());
        p2.setName("Product 2");
        p2.setSku("12345");
        p2.setPrice(BigDecimal.ONE);
        o1.getProducts().addAll(Arrays.asList(p1,p2));
        given(productRepository.findAllById(Arrays.asList(p1.getSku(),p2.getSku()))).willReturn(Arrays.asList(p1));
        PlaceOrderUseCase.Response response = placeOrderUseCase.execute(new PlaceOrderUseCase.Request(o1));
    }

    @Test(expected = MissingRequiredFieldsException.class)
    public void PlaceOrderShouldVerifyId() {
        Order o1 = new Order();
        o1.setBuyerEmail("test@test.es");
        Product p1 = new Product();
        p1.setCreationDate(LocalDateTime.now());
        p1.setName("Product 1");
        p1.setSku("12344");
        p1.setPrice(BigDecimal.TEN);
        Product p2 = new Product();
        p2.setCreationDate(LocalDateTime.now());
        p2.setName("Product 2");
        p2.setSku("12345");
        p2.setPrice(BigDecimal.ONE);
        o1.getProducts().addAll(Arrays.asList(p1,p2));
        PlaceOrderUseCase.Response response = placeOrderUseCase.execute(new PlaceOrderUseCase.Request(o1));
    }

    @Test(expected = MissingRequiredFieldsException.class)
    public void PlaceOrderShouldVerifyBuyerEmail() {
        Order o1 = new Order();
        o1.setId(1);
        Product p1 = new Product();
        p1.setCreationDate(LocalDateTime.now());
        p1.setName("Product 1");
        p1.setSku("12344");
        p1.setPrice(BigDecimal.TEN);
        Product p2 = new Product();
        p2.setCreationDate(LocalDateTime.now());
        p2.setName("Product 2");
        p2.setSku("12345");
        p2.setPrice(BigDecimal.ONE);
        o1.getProducts().addAll(Arrays.asList(p1,p2));
        PlaceOrderUseCase.Response response = placeOrderUseCase.execute(new PlaceOrderUseCase.Request(o1));
    }
}