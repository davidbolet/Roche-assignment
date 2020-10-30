package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.ProductAlreadyExistsException;
import com.github.davidbolet.assignment.exception.ProductNotFoundException;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.AdditionalAnswers.returnsFirstArg;

@RunWith(MockitoJUnitRunner.class)
public class UpdateProductUseCaseTest {

    @Mock
    private transient ProductRepository productRepository;

    private transient UpdateProductUseCase updateProductUseCase;

    @Before
    public void setUp() {
        updateProductUseCase = new UpdateProductUseCase(productRepository);
    }

    @Test
    public void updateProductShouldWork() {
        Product p1 = new Product();
        p1.setName("TestProductChanged");
        p1.setSku("12345");
        p1.setPrice(BigDecimal.valueOf(100));
        Product updated = new Product();
        updated.setName("TestProduct");
        updated.setSku("12345");
        updated.setPrice(BigDecimal.valueOf(120));
        given(productRepository.getOne(p1.getSku())).willReturn(updated);
        given(productRepository.save(any(Product.class))).will(returnsFirstArg());
        UpdateProductUseCase.Response response = updateProductUseCase.execute(new UpdateProductUseCase.Request(p1));
        assertThat(response.getProduct().getName()).isEqualTo(p1.getName());
        assertThat(response.getProduct().getPrice()).isEqualTo(p1.getPrice());
    }

    @Test(expected = ProductNotFoundException.class)
    public void updateProductShouldCheckIfExist() {
        Product p1 = new Product();
        p1.setName("TestProduct");
        p1.setSku("12345");
        p1.setPrice(BigDecimal.valueOf(100));
        given(productRepository.getOne(p1.getSku())).willThrow(EntityNotFoundException.class);
        updateProductUseCase.execute(new UpdateProductUseCase.Request(p1));
    }
}