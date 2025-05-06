package org.booking_hotel.daos.room_type_facilities;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.room_type_facilities.dto.RoomTypeFacilityDto;
import org.booking_hotel.jooq.model.tables.records.RoomTypeFacilityRecord;

import java.util.List;

public interface RoomTypeFacilityDao extends BaseDao<RoomTypeFacilityDto, RoomTypeFacilityRecord, Long> {
    
    List<RoomTypeFacilityDto> getByRoomTypeId(Long roomTypeId);

    List<RoomTypeFacilityDto> getByFacilityId(Long facilityId);
}