package com.github.davidbolet.assignment.controller;

import com.github.davidbolet.assignment.domain.Order;
import com.github.davidbolet.assignment.domain.Product;
import com.github.davidbolet.assignment.exception.MissingRequiredFieldsException;
import com.github.davidbolet.assignment.exception.OrderAlreadyExistsException;
import com.github.davidbolet.assignment.exception.ProductAlreadyExistsException;
import com.github.davidbolet.assignment.exception.ProductNotFoundException;
import com.github.davidbolet.assignment.useCases.CreateProductUseCase;
import com.github.davidbolet.assignment.useCases.PlaceOrderUseCase;
import com.github.davidbolet.assignment.useCases.RetrieveOrdersUseCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/orders/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Api("ordersApi")
public class OrderController {

    @Autowired
    private transient PlaceOrderUseCase placeOrderUseCase;

    @Autowired
    private transient RetrieveOrdersUseCase retrieveOrdersUseCase;

    @ApiOperation(value = "Place order", notes = "Creates a new order")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Order created", response = Product.class),
            @ApiResponse(code = 400, message = "Invalid input: missing required fields", response = MissingRequiredFieldsException.class),
            @ApiResponse(code = 404, message = "Not all specified products exist", response = ProductNotFoundException.class),
            @ApiResponse(code = 409, message = "Order with same id already exists", response = OrderAlreadyExistsException.class)})
    @RequestMapping(value = {"/order"},consumes =  MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    Order placeOrder( @RequestBody Order order) {
        return placeOrderUseCase.execute(new PlaceOrderUseCase.Request(order)).getOrder();
    }

    @ApiOperation(value = "Retrieve orders", notes = "Retrieve all orders within start time and end time")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Order created", response = Product.class),
            @ApiResponse(code = 400, message = "Invalid input: dates are not correct", response = MissingRequiredFieldsException.class),
            })
    @RequestMapping(value = {"/orders"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<Order> retrieveOrder(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startMoment,
                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endMoment) {
        return retrieveOrdersUseCase.execute(new RetrieveOrdersUseCase.Request(startMoment,endMoment)).getOrders();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IllegalArgumentException handleIllegalArgumentException(IllegalArgumentException ex) {
        return ex;
    }
}
