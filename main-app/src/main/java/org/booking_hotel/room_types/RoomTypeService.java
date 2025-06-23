package org.booking_hotel.room_types;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.booking_hotel.daos.room_types.RoomTypeDao;
import org.booking_hotel.daos.room_types.dto.RoomTypeDto;
import org.booking_hotel.jooq.model.tables.records.RoomTypeRecord;
import org.booking_hotel.room_types.model.RoomTypeSaveRequest;
import org.booking_hotel.room_types.model.RoomTypeSaveResponse;

import java.util.List;
import java.util.function.Consumer;
import java.time.OffsetDateTime;


@RequestScoped
@Transactional
public class RoomTypeService {
    @Inject
    RoomTypeDao roomTypeDao;

    public List<RoomTypeDto> getAllRoomTypes() {
        return roomTypeDao.getAll();
    }

    public RoomTypeDto getRoomTypeById(Long id) {
        return roomTypeDao.getById(id);
    }

    public RoomTypeSaveResponse saveRoomType(RoomTypeSaveRequest req) {
        Consumer<RoomTypeRecord> fn = record -> {
            record.setHotelId(req.hotelId());
            record.setBedTypeId(req.bedTypeId());
            record.setName(req.name());
            record.setCount(req.count());

            record.setCreatedAt(OffsetDateTime.now());
            record.setUpdatedAt(OffsetDateTime.now()); // Update the timestamp on creation or update
            record.setRemoved(false);
        };

        RoomTypeDto createdRoomType = req.id() == null ? roomTypeDao.insert(fn) : roomTypeDao.updateById(fn, req.id());

        return new RoomTypeSaveResponse(createdRoomType.id());
    }

}