package org.booking_hotel.orders;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.orders.dto.OrderDto;
import org.booking_hotel.jooq.model.tables.Orders;
import org.booking_hotel.jooq.model.tables.records.OrderRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class OrderDaoImpl implements OrderDao {

    private final Orders o = Orders.ORDERS.as("o");

    @Inject
    DSLContext dsl;

    public List<OrderDto> getAll() {
        return dsl.selectFrom(o)
                .where(o.REMOVED.isFalse())
                .fetch().stream().map(OrderDto::of).toList();
    }

    public OrderDto getById(Long id) {
        return dsl.selectFrom(o)
                .where(o.REMOVED.isFalse(), o.ID.eq(id))
                .fetchSingle(OrderDto::of);
    }

    @Override
    public OrderDto insert(Consumer<OrderRecord> fn) {
        var record = new OrderRecord();
        fn.accept(record);
        return dsl.insertInto(o)
                .set(record)
                .returning()
                .fetchSingle(OrderDto::of);
    }

    @Override
    public OrderDto updateById(Consumer<OrderRecord> fn, Long id) {
        var record = new OrderRecord();
        fn.accept(record);
        return dsl.update(o)
                .set(record)
                .where(o.REMOVED.isFalse(), o.ID.eq(id))
                .returning()
                .fetchSingle(OrderDto::of);
    }

    @Override
    public Integer deleteById(Long id) {
        return dsl.update(o)
                .set(o.REMOVED, true)
                .where(o.REMOVED.isFalse(), o.ID.eq(id))
                .execute();
    }
    
    @Override
    public List<OrderDto> getByHotelId(Long hotelId) {
        return dsl.selectFrom(o)
                .where(o.REMOVED.isFalse(), o.HOTEL_ID.eq(hotelId))
                .fetch().stream().map(OrderDto::of).toList();
    }
    
    @Override
    public List<OrderDto> getByUserId(Long userId) {
        return dsl.selectFrom(o)
                .where(o.REMOVED.isFalse(), o.USER_ID.eq(userId))
                .fetch().stream().map(OrderDto::of).toList();
    }
}