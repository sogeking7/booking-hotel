package org.booking_hotel.orders;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.daos.orders.dto.OrderDto;
import org.booking_hotel.orders.model.OrderModel;
import org.booking_hotel.orders.model.OrderSaveRequest;
import org.booking_hotel.orders.model.OrderSaveResponse;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/orders")
@Tag(name = "Order", description = "Operations related to Order items")
public class OrderResource {
    @Inject
    OrderService orderService;

    @GET
    @Path("/{id}")
    public OrderModel getOrderById(@PathParam("id") Long id) {
        OrderDto order = orderService.getOrderById(id);
        return OrderModel.of(order);
    }

    @POST
    public OrderSaveResponse saveOrder(@Valid OrderSaveRequest req) throws BusinessException {
        return orderService.saveOrder(req);
    }

    @GET
    public List<OrderModel> getAllOrders() {
        return orderService.getAllOrders().stream().map(OrderModel::of).toList();
    }

    @GET
    @Path("/hotel/{hotelId}")
    public List<OrderModel> getOrdersByHotelId(@PathParam("hotelId") Long hotelId) {
        return orderService.getOrdersByHotelId(hotelId).stream().map(OrderModel::of).toList();
    }

    @GET
    @Path("/user/{userId}")
    public List<OrderModel> getOrdersByUserId(@PathParam("userId") Long userId) {
        return orderService.getOrdersByUserId(userId).stream().map(OrderModel::of).toList();
    }

    @DELETE
    @Path("/{id}")
    public void deleteOrderById(@PathParam("id") Long id) {
        orderService.deleteOrderById(id);
    }
}