package org.booking_hotel.hotels;

import org.booking_hotel.hotels.dto.HotelDto;
import org.booking_hotel.jooq.model.tables.records.HotelRecord;

import java.util.List;
import java.util.function.Consumer;

public interface HotelDao {

    List<HotelDto> getAll();

    HotelDto getById(Long id);

    HotelDto insert(Consumer<HotelRecord> fn);

    HotelDto updateById(Consumer<HotelRecord> fn, Long id);

    Integer deleteById(Long id);
    
    Boolean existsById(Long id);
}