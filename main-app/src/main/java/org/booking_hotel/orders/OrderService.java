package org.booking_hotel.orders;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.daos.hotels.HotelDao;
import org.booking_hotel.daos.orders.OrderDao;
import org.booking_hotel.daos.orders.dto.OrderDto;
import org.booking_hotel.daos.users.UserDao;
import org.booking_hotel.jooq.model.tables.records.OrderRecord;
import org.booking_hotel.orders.model.OrderSaveRequest;
import org.booking_hotel.orders.model.OrderSaveResponse;
import org.booking_hotel.utils.BusinessException;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class OrderService {
    @Inject
    OrderDao orderDao;

    @Inject
    HotelDao hotelDao;

    @Inject
    UserDao userDao;

    public List<OrderDto> getAllOrders() {
        return orderDao.getAll();
    }

    public OrderDto getOrderById(Long id) {
        return orderDao.getById(id);
    }

    public List<OrderDto> getOrdersByHotelId(Long hotelId) {
        return orderDao.getByHotelId(hotelId);
    }

    public List<OrderDto> getOrdersByUserId(Long userId) {
        return orderDao.getByUserId(userId);
    }

    public OrderSaveResponse saveOrder(OrderSaveRequest req) throws BusinessException {
        // Verify that the hotel exists
        if (!hotelDao.existsById(req.hotelId())) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "order.save.hotelIsNotExists",
                    "Hotel with id " + req.hotelId() + " does not exist"
            );
        }

        // Verify that the user exists
        // Note: We would need to implement existsById in UserDao
        // For now, we'll assume it exists or handle it differently

        Consumer<OrderRecord> fn = record -> {
            record.setFromDate(req.fromDate());
            record.setToData(req.toData());
            record.setHotelId(req.hotelId());
            record.setUserId(req.userId());
        };

        OrderDto createdOrder = req.id() == null ? orderDao.insert(fn) : orderDao.updateById(fn, req.id());

        return new OrderSaveResponse(
                createdOrder.id(),
                createdOrder.fromDate(),
                createdOrder.toData(),
                createdOrder.hotelId(),
                createdOrder.userId()
        );
    }

    public void deleteOrderById(Long id) {
        orderDao.removeById(id);
    }
}