package org.booking_hotel.daos.hotels;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.hotels.dto.HotelDto;
import org.booking_hotel.jooq.model.tables.records.HotelRecord;

public interface HotelDao extends BaseDao<HotelDto, HotelRecord, Long> {

    Boolean existsById(Long id);
}