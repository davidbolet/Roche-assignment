package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.MissingRequiredFieldsException;
import com.github.davidbolet.assignment.exception.ProductNotFoundException;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;

@Service
public class UpdateProductUseCase {

    private final ProductRepository productRepository;

    public UpdateProductUseCase(ProductRepository repository) {
        productRepository = repository;
    }

    public Response execute(Request request) {
        Product toUpdate;
        if (isNull(request.getProduct().getSku())) {
            throw new MissingRequiredFieldsException();
        }
        try {
            toUpdate = productRepository.findById(request.getProduct().getSku()).orElseThrow();
        } catch (NoSuchElementException | EntityNotFoundException enf) {
            throw new ProductNotFoundException(String.format("Product with SKU %s does not exist!", request.getProduct().getSku()));
        }
        if (Objects.nonNull(request.getProduct().getName())) {
            toUpdate.setName(request.getProduct().getName());
        }
        if (Objects.nonNull(request.getProduct().getPrice())) {
            toUpdate.setPrice(request.getProduct().getPrice());
        }
        if (Objects.nonNull(request.getProduct().getCreationDate())) {
            toUpdate.setCreationDate(request.getProduct().getCreationDate());
        }
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
