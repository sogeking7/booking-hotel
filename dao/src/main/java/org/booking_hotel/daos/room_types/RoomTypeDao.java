package org.booking_hotel.daos.room_types;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.room_types.dto.RoomTypeDto;
import org.booking_hotel.jooq.model.tables.records.RoomTypeRecord;

public interface RoomTypeDao extends BaseDao<RoomTypeDto, RoomTypeRecord, Long> {

    default void deleteRoomTypeById(Long id) {
        removeById(id);
    }
}