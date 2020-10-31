package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.MissingRequiredFieldsException;
import com.github.davidbolet.assignment.exception.ProductAlreadyExistsException;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class CreateProductUseCase {

    private final ProductRepository productRepository;

    public CreateProductUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Response execute(Request request) {
        checkRequiredFields(request.getProduct());
        if (productRepository.existsById(request.getProduct().getSku())) {
            throw new ProductAlreadyExistsException(String.format("Product with SKU %s already exists!", request.getProduct().getSku()));
        }
        return new Response(productRepository.save(request.getProduct()));
    }

    private void checkRequiredFields(Product product) {
        if (isNull(product.getSku())||isNull(product.getName())||isNull(product.getPrice())||isNull(product.getCreationDate())) {
            throw new MissingRequiredFieldsException();
        }
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
