package org.booking_hotel.daos.orders;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.orders.dto.OrderDto;
import org.booking_hotel.jooq.model.tables.records.OrderRecord;

import java.util.List;

public interface OrderDao extends BaseDao<OrderDto, OrderRecord, Long> {

    List<OrderDto> getByHotelId(Long hotelId);

    List<OrderDto> getByUserId(Long userId);

    List<OrderDto> getByRoomTypeUserId(Long roomTypeUserId);
}