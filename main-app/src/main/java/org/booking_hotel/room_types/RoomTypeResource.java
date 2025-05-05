package org.booking_hotel.room_types;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.room_types.dto.RoomTypeDto;
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

    @POST
    public RoomTypeSaveResponse saveRoomType(@Valid RoomTypeSaveRequest req) {
        return roomTypeService.saveRoomType(req);
    }

    @GET
    public List<RoomTypeModel> getAllRoomTypes() {
        return roomTypeService.getAllRoomTypes().stream().map(RoomTypeModel::of).toList();
    }

    @DELETE
    @Path("/{id}")
    public void deleteRoomTypeById(@PathParam("id") Long id) {
        roomTypeService.deleteRoomTypeById(id);
    }
}