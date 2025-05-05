package org.booking_hotel.room_types;

import org.booking_hotel.room_types.dto.RoomTypeDto;
import org.booking_hotel.jooq.model.tables.records.RoomTypeRecord;

import java.util.List;
import java.util.function.Consumer;

public interface RoomTypeDao {

    List<RoomTypeDto> getAll();

    RoomTypeDto getById(Long id);

    RoomTypeDto insert(Consumer<RoomTypeRecord> fn);

    RoomTypeDto updateById(Consumer<RoomTypeRecord> fn, Long id);

    Integer deleteById(Long id);
}