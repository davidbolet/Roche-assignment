package com.github.davidbolet.assignment.useCases;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RetrieveAllProductsUseCase {

    private final ProductRepository productRepository;

    public RetrieveAllProductsUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public RetrieveAllProductsUseCase.Response execute(RetrieveAllProductsUseCase.Request request) {
        return new Response(productRepository.findAll());
    }

    public static class Request {
    }

    public static class Response {
        private final List<Product> products;

        public Response(List<Product> productList) {
            this.products = productList;
        }

        public List<Product> getProducts() {
            return products;
        }
    }
}
