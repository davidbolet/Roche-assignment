package com.github.davidbolet.assignment.controller;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.useCases.CreateProductUseCase;
import com.github.davidbolet.assignment.useCases.RetrieveAllProductsUseCase;
import com.github.davidbolet.assignment.useCases.UpdateProductUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/products")
public class ProductController {

    CreateProductUseCase createProductUseCase;
    RetrieveAllProductsUseCase retrieveAllProductsUseCase;
    UpdateProductUseCase updateProductUseCase;

    @RequestMapping(value = {"/product"}, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Product createProduct(@RequestBody Product product) {
        return createProductUseCase.execute(new CreateProductUseCase.Request(product)).getProduct();
    }

    @RequestMapping( method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Product> listProducts() {
        return retrieveAllProductsUseCase.execute(new RetrieveAllProductsUseCase.Request()).getProducts();
    }

    @RequestMapping(value = {"/product"}, method = RequestMethod.PATCH)
    public @ResponseBody
    Product updateProduct(@RequestBody Product product) {
        return updateProductUseCase.execute(new UpdateProductUseCase.Request(product)).getProduct();
    }

    @RequestMapping(value = {"/product/{sku}"}, method = RequestMethod.DELETE)
    public @ResponseBody
    Product deleteProduct(@PathVariable("sku") String sku, @RequestBody Product product) {
        throw new UnsupportedOperationException();
    }

}

