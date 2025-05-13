package org.booking_hotel.room_type_facilities;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.daos.room_type_facilities.dto.RoomTypeFacilityDto;
import org.booking_hotel.room_type_facilities.model.RoomTypeFacilityModel;
import org.booking_hotel.room_type_facilities.model.RoomTypeFacilitySaveRequest;
import org.booking_hotel.room_type_facilities.model.RoomTypeFacilitySaveResponse;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/room-type-facilities")
@Tag(name = "RoomTypeFacility", description = "Operations related to Room Type Facility items")
public class RoomTypeFacilityResource {
    @Inject
    RoomTypeFacilityService roomTypeFacilityService;

    @GET
    @Path("/{id}")
    public RoomTypeFacilityModel getRoomTypeFacilityById(@PathParam("id") Long id) {
        RoomTypeFacilityDto roomTypeFacility = roomTypeFacilityService.getRoomTypeFacilityById(id);
        return RoomTypeFacilityModel.of(roomTypeFacility);
    }

    @RolesAllowed("admin")
    @POST
    public RoomTypeFacilitySaveResponse saveRoomTypeFacility(@Valid RoomTypeFacilitySaveRequest req) throws BusinessException {
        return roomTypeFacilityService.saveRoomTypeFacility(req);
    }

    @GET
    public List<RoomTypeFacilityModel> getAllRoomTypeFacilities() {
        return roomTypeFacilityService.getAllRoomTypeFacilities().stream().map(RoomTypeFacilityModel::of).toList();
    }

    @GET
    @Path("/room-type/{roomTypeId}")
    public List<RoomTypeFacilityModel> getRoomTypeFacilitiesByRoomTypeId(@PathParam("roomTypeId") Long roomTypeId) {
        return roomTypeFacilityService.getRoomTypeFacilitiesByRoomTypeId(roomTypeId)
                .stream().map(RoomTypeFacilityModel::of).toList();
    }

    @GET
    @Path("/facility/{facilityId}")
    public List<RoomTypeFacilityModel> getRoomTypeFacilitiesByFacilityId(@PathParam("facilityId") Long facilityId) {
        return roomTypeFacilityService.getRoomTypeFacilitiesByFacilityId(facilityId)
                .stream().map(RoomTypeFacilityModel::of).toList();
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/{id}")
    public void deleteRoomTypeFacilityById(@PathParam("id") Long id) {
        roomTypeFacilityService.deleteRoomTypeFacilityById(id);
    }
}