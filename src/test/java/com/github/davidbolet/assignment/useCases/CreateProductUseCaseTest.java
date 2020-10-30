package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.ProductAlreadyExistsException;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CreateProductUseCaseTest {

    @Mock
    private transient ProductRepository productRepository;

    private transient CreateProductUseCase createProductUseCase;

    @Before
    public void setUp() {
        createProductUseCase = new CreateProductUseCase(productRepository);
    }

    @Test
    public void createProductShouldWork() {
        Product p1 = new Product();
        p1.setName("TestProduct");
        p1.setSku("12345");
        p1.setPrice(BigDecimal.valueOf(100));
        given(productRepository.existsById(p1.getSku())).willReturn(false);
        given(productRepository.save(p1)).willReturn(p1);
        CreateProductUseCase.Response response = createProductUseCase.execute(new CreateProductUseCase.Request(p1));
        assertThat(response.getProduct()).isEqualTo(p1);
    }


    @Test(expected = ProductAlreadyExistsException.class)
    public void createProductShouldCheckIfExist() {
        Product p1 = new Product();
        p1.setName("TestProduct");
        p1.setSku("12345");
        p1.setPrice(BigDecimal.valueOf(100));
        given(productRepository.existsById(p1.getSku())).willReturn(true);
        CreateProductUseCase.Response response = createProductUseCase.execute(new CreateProductUseCase.Request(p1));
    }
}