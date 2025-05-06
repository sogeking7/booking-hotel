package org.booking_hotel.room_type_facilities;

import org.booking_hotel.room_type_facilities.dto.RoomTypeFacilityDto;
import org.booking_hotel.jooq.model.tables.records.RoomTypeFacilityRecord;

import java.util.List;
import java.util.function.Consumer;

public interface RoomTypeFacilityDao {

    List<RoomTypeFacilityDto> getAll();

    RoomTypeFacilityDto getById(Long id);

    RoomTypeFacilityDto insert(Consumer<RoomTypeFacilityRecord> fn);

    RoomTypeFacilityDto updateById(Consumer<RoomTypeFacilityRecord> fn, Long id);

    Integer deleteById(Long id);
    
    List<RoomTypeFacilityDto> getByRoomTypeId(Long roomTypeId);
    
    List<RoomTypeFacilityDto> getByFacilityId(Long facilityId);
}