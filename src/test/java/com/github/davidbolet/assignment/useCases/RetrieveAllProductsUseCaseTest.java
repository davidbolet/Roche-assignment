package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class RetrieveAllProductsUseCaseTest {

    @Mock
    private transient ProductRepository productRepository;

    private transient RetrieveAllProductsUseCase retrieveAllProductsUseCase;

    @Before
    public void setUp() {
        retrieveAllProductsUseCase = new RetrieveAllProductsUseCase(productRepository);
    }

    @Test
    public void RetrieveAllProductsShouldWork() {
        Product p1 = new Product();
        p1.setName("TestProduct1");
        p1.setSku("12344");
        Product p2 = new Product();
        p2.setName("TestProduct2");
        p2.setSku("12345");
        List<Product> productList = Arrays.asList(p1,p2);
        given(productRepository.findAll()).willReturn(productList);
        RetrieveAllProductsUseCase.Response response = retrieveAllProductsUseCase.execute(new RetrieveAllProductsUseCase.Request());
        assertThat(response.getProducts()).isEqualTo(productList);
    }

    @Test
    public void RetrieveAllProductsShouldReturnEmptyListIfNoProducts() {
        given(productRepository.findAll()).willReturn(Collections.emptyList());
        RetrieveAllProductsUseCase.Response response = retrieveAllProductsUseCase.execute(new RetrieveAllProductsUseCase.Request());
        assertThat(response.getProducts()).isEqualTo(Collections.emptyList());
    }


}