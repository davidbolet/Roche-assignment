package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.ProductAlreadyExistsException;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Response execute(Request request) {
        if (productRepository.existsById(request.getProduct().getSku())) {
            throw new ProductAlreadyExistsException(String.format("Product with SKU %s already exists!", request.getProduct().getSku()));
        }
        return new Response(productRepository.save(request.getProduct()));
    }

    public static class Request {
        private final Product product;

        public Request(Product product) {
            this.product = product;
        }

        public Product getProduct() {
            return this.product;
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
