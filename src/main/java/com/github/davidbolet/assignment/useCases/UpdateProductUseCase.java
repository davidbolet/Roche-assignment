package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.ProductNotFoundException;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository repository) {
        productRepository = repository;
    }

    public Response execute(Request request) {
        Product toUpdate;
        try {
            toUpdate = productRepository.getOne(request.getProduct().getSku());
        } catch (EntityNotFoundException enf) {
            throw new ProductNotFoundException(String.format("Product with SKU %s does not exist!", request.getProduct().getSku()));
        }
        toUpdate.setName(request.getProduct().getName());
        toUpdate.setPrice(request.getProduct().getPrice());
        return new Response(productRepository.save(toUpdate));
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
