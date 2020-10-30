package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class DeleteProductUseCaseTest {

    @Mock
    private transient ProductRepository productRepository;

    private transient DeleteProductUseCase deleteProductUseCase;

    @Before
    public void setUp() {
        deleteProductUseCase = new DeleteProductUseCase(productRepository);
    }

    @Test
    public void deleteProductShouldWork() {
        Product p1 = new Product();
        p1.setName("TestProduct");
        p1.setSku("12345");
        p1.setPrice(BigDecimal.valueOf(100));
        given(productRepository.getOne(p1.getSku())).willReturn(p1);
        given(productRepository.save(any(Product.class))).will(returnsFirstArg());
        Product deleted = deleteProductUseCase.execute(new DeleteProductUseCase.Request(p1.getSku())).getProduct();
        assertThat(deleted).isEqualTo(p1);
    }

    @Test(expected = ProductNotFoundException.class)
    public void deleteProductShouldCheckIfExist() {
        Product p1 = new Product();
        p1.setName("TestProduct");
        p1.setSku("12345");
        p1.setPrice(BigDecimal.valueOf(100));
        given(productRepository.getOne(p1.getSku())).willThrow(EntityNotFoundException.class);
        deleteProductUseCase.execute(new DeleteProductUseCase.Request(p1.getSku()));
    }

}