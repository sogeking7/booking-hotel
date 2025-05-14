package org.booking_hotel.cities;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.cities.model.CityModel;
import org.booking_hotel.cities.model.CitySaveRequest;
import org.booking_hotel.cities.model.CitySaveResponse;
import org.booking_hotel.daos.cities.dto.CityDto;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/cities")
@Tag(name = "City", description = "Operations related to City items")
public class CityResource {
    @Inject
    CityService cityService;

    @GET
    @Path("/{id}")
    public CityModel getCityById(@PathParam("id") Long id) {
        CityDto country = cityService.getCityById(id);
        return CityModel.of(country);
    }


    @POST
    public CitySaveResponse saveCity(@Valid CitySaveRequest req) throws BusinessException {
        return cityService.saveCity(req);
    }

    @GET
    public List<CityModel> getAllCities() {
        return cityService.getAllCities().stream().map(CityModel::of).toList();
    }


    @DELETE
    @Path("/{id}")
    public void deleteCityById(@PathParam("id") Long id) {
        cityService.deleteCityById(id);
    }
}
