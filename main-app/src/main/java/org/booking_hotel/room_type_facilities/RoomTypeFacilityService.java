package org.booking_hotel.room_type_facilities;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.daos.facilities.FacilityDao;
import org.booking_hotel.daos.room_type_facilities.RoomTypeFacilityDao;
import org.booking_hotel.daos.room_type_facilities.dto.RoomTypeFacilityDto;
import org.booking_hotel.jooq.model.tables.records.RoomTypeFacilityRecord;
import org.booking_hotel.room_type_facilities.model.RoomTypeFacilitySaveRequest;
import org.booking_hotel.room_type_facilities.model.RoomTypeFacilitySaveResponse;
import org.booking_hotel.utils.BusinessException;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class RoomTypeFacilityService {
    @Inject
    RoomTypeFacilityDao roomTypeFacilityDao;

    @Inject
    FacilityDao facilityDao;

    // We would also inject RoomTypeDao if it existed
    // @Inject
    // RoomTypeDao roomTypeDao;

    public List<RoomTypeFacilityDto> getAllRoomTypeFacilities() {
        return roomTypeFacilityDao.getAll();
    }

    public RoomTypeFacilityDto getRoomTypeFacilityById(Long id) {
        return roomTypeFacilityDao.getById(id);
    }

    public List<RoomTypeFacilityDto> getRoomTypeFacilitiesByRoomTypeId(Long roomTypeId) {
        return roomTypeFacilityDao.getByRoomTypeId(roomTypeId);
    }

    public List<RoomTypeFacilityDto> getRoomTypeFacilitiesByFacilityId(Long facilityId) {
        return roomTypeFacilityDao.getByFacilityId(facilityId);
    }

    public RoomTypeFacilitySaveResponse saveRoomTypeFacility(RoomTypeFacilitySaveRequest req) throws BusinessException {
        // Verify that the facility exists
        try {
            facilityDao.getById(req.facilityId());
        } catch (Exception e) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "roomTypeFacility.save.facilityIsNotExists",
                    "Facility with id " + req.facilityId() + " does not exist"
            );
        }

        // Verify that the room type exists
        // We would need to implement this check if we had a RoomTypeDao
        // For now, we'll assume it exists or handle it differently

        Consumer<RoomTypeFacilityRecord> fn = record -> {
            record.setRoomTypeId(req.roomTypeId());
            record.setFacilityId(req.facilityId());
        };

        RoomTypeFacilityDto createdRoomTypeFacility = req.id() == null ?
                roomTypeFacilityDao.insert(fn) :
                roomTypeFacilityDao.updateById(fn, req.id());

        return new RoomTypeFacilitySaveResponse(
                createdRoomTypeFacility.id(),
                createdRoomTypeFacility.roomTypeId(),
                createdRoomTypeFacility.facilityId()
        );
    }

    public void deleteRoomTypeFacilityById(Long id) {
        roomTypeFacilityDao.removeById(id);
    }
}