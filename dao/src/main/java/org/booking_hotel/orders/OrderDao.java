package org.booking_hotel.orders;

import org.booking_hotel.orders.dto.OrderDto;
import org.booking_hotel.jooq.model.tables.records.OrderRecord;

import java.util.List;
import java.util.function.Consumer;

public interface OrderDao {

    List<OrderDto> getAll();

    OrderDto getById(Long id);

    OrderDto insert(Consumer<OrderRecord> fn);

    OrderDto updateById(Consumer<OrderRecord> fn, Long id);

    Integer deleteById(Long id);
    
    List<OrderDto> getByHotelId(Long hotelId);
    
    List<OrderDto> getByUserId(Long userId);
}