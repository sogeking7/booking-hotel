package org.booking_hotel.hotels;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.daos.hotels.dto.HotelDto;
import org.booking_hotel.hotels.model.HotelDetailModel;
import org.booking_hotel.hotels.model.HotelModel;
import org.booking_hotel.hotels.model.HotelSaveRequest;
import org.booking_hotel.hotels.model.HotelSaveResponse;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/hotels")
@Tag(name = "Hotel", description = "Operations related to Hotel items")
public class HotelResource {
    @Inject
    HotelService hotelService;

    @GET
    @Path("/{id}")
    public HotelModel getHotelById(@PathParam("id") Long id) {
        HotelDto hotel = hotelService.getHotelById(id);
        return HotelModel.of(hotel);
    }

    @GET
    @Path("/{id}/details")
    public HotelDetailModel getHotelDetails(@PathParam("id") Long id) {
        return hotelService.getHotelDetails(id);
    }

    @RolesAllowed("admin")
    @POST
    public HotelSaveResponse saveHotel(@Valid HotelSaveRequest req) throws BusinessException {
        return hotelService.saveHotel(req);
    }

    @GET
    @Path("/")
    public List<HotelModel> getAllHotels() {
        return hotelService.getAllHotels().stream().map(HotelModel::of).toList();
    }

    @GET
    @Path("/search")
    @Produces("application/json")
    @Consumes("application/json")
    public List<HotelModel> searchHotels(@QueryParam("search") String search) {
        return hotelService.searchHotels(search).stream().map(HotelModel::of).toList();
    }

    @RolesAllowed("admin")
    @DELETE
    @Path("/{id}")
    public void deleteHotelById(@PathParam("id") Long id) {
        hotelService.deleteHotelById(id);
    }
}
