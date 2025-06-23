package org.booking_hotel.room_types;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.daos.room_types.dto.RoomTypeDto;
import org.booking_hotel.room_types.model.RoomTypeModel;
import org.booking_hotel.room_types.model.RoomTypeSaveRequest;
import org.booking_hotel.room_types.model.RoomTypeSaveResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/room-types")
@Tag(name = "RoomType", description = "Operations related to Room Type items")
public class RoomTypeResource {
    @Inject
    RoomTypeService roomTypeService;

    @GET
    @Path("/{id}")
    public RoomTypeModel getRoomTypeById(@PathParam("id") Long id) {
        RoomTypeDto roomType = roomTypeService.getRoomTypeById(id);
        return RoomTypeModel.of(roomType);
    }

    @RolesAllowed("admin")
    @POST
    public RoomTypeSaveResponse saveRoomType(@Valid RoomTypeSaveRequest req) {
        System.out.println("Received RoomTypeSaveRequest: " + req);

        try {
            return roomTypeService.saveRoomType(req);
        } catch (Exception e) {
            e.printStackTrace();


            throw new InternalServerErrorException("Failed to save room type: " + e.getMessage());
        }
    }


    @GET
    public List<RoomTypeModel> getAllRoomTypes() {
        return roomTypeService.getAllRoomTypes().stream().map(RoomTypeModel::of).toList();
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/{id}")
    public void deleteRoomTypeById(@PathParam("id") Long id) {
        roomTypeService.roomTypeDao.deleteRoomTypeById(id);
    }
}