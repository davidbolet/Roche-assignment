package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.repository.ProductRepository;
import com.github.davidbolet.assignment.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class DeleteProductUseCase {
    private final ProductRepository productRepository;

    public DeleteProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public DeleteProductUseCase.Response execute(DeleteProductUseCase.Request request) {
        Product toDelete;
        try {
            toDelete = productRepository.findById(request.getSku()).orElseThrow();
        } catch (NoSuchElementException | EntityNotFoundException enf) {
            throw new ProductNotFoundException(String.format("Product with SKU %s does not exist!", request.getSku()));
        }
        toDelete.setDeleted(LocalDateTime.now());
        return new DeleteProductUseCase.Response(productRepository.save(toDelete));
    }

    public static class Request {
        private final String sku;

        public Request(String sku) {
            this.sku = sku;
        }

        public String getSku() {
            return this.sku;
        }
    }

    public static class Response {
        private final Product product;

        public Response(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return product;
        }
    }
}
