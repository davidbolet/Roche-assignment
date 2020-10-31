package com.github.davidbolet.assignment.controller;

import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.MissingRequiredFieldsException;
import com.github.davidbolet.assignment.exception.ProductAlreadyExistsException;
import com.github.davidbolet.assignment.exception.ProductNotFoundException;
import com.github.davidbolet.assignment.useCases.CreateProductUseCase;
import com.github.davidbolet.assignment.useCases.DeleteProductUseCase;
import com.github.davidbolet.assignment.useCases.RetrieveAllProductsUseCase;
import com.github.davidbolet.assignment.useCases.UpdateProductUseCase;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/products/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api("productsApi")
public class ProductController {
    @Autowired
    CreateProductUseCase createProductUseCase;

    @Autowired
    RetrieveAllProductsUseCase retrieveAllProductsUseCase;

    @Autowired
    UpdateProductUseCase updateProductUseCase;

    @Autowired
    DeleteProductUseCase deleteProductUseCase;

    @ApiOperation(value = "create Product", notes = "Creates a new product")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Product created", response = Product.class),
            @ApiResponse(code = 400, message = "Invalid input: missing required fields", response = MissingRequiredFieldsException.class),
            @ApiResponse(code = 409, message = "Product with same sku already exists", response = ProductAlreadyExistsException.class)})
    @RequestMapping(value = {"/product"},consumes =  MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Product createProduct( @RequestBody Product product) {
        return createProductUseCase.execute(new CreateProductUseCase.Request(product)).getProduct();
    }

    @RequestMapping(value = {"/products"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<Product> listProducts() {
        return retrieveAllProductsUseCase.execute(new RetrieveAllProductsUseCase.Request()).getProducts();
    }

    @ApiOperation(value = "Update Product", notes = "Updates an existing product identified by sku. Only provided fields are updated")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Product successfully updated", response = Product.class),
            @ApiResponse(code = 400, message = "Invalid input: missing sku field", response = MissingRequiredFieldsException.class),
            @ApiResponse(code = 404, message = "Product with given sku not exists", response = ProductNotFoundException.class)})
    @RequestMapping(value = {"/product"}, consumes =  MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.PATCH)
    public @ResponseBody
    Product updateProduct(@RequestBody Product product) {
        return updateProductUseCase.execute(new UpdateProductUseCase.Request(product)).getProduct();
    }

    @RequestMapping(value = {"/product/{sku}"}, method = RequestMethod.DELETE)
    public @ResponseBody
    Product deleteProduct(@PathVariable("sku") String sku) {
        return deleteProductUseCase.execute(new DeleteProductUseCase.Request(sku)).getProduct();
    }

}

