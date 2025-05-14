package org.booking_hotel.facilities;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.daos.facilities.dto.FacilityDto;
import org.booking_hotel.facilities.model.FacilityModel;
import org.booking_hotel.facilities.model.FacilitySaveRequest;
import org.booking_hotel.facilities.model.FacilitySaveResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/facilities")
@Tag(name = "Facility", description = "Operations related to Facility items")
public class FacilityResource {
    @Inject
    FacilityService facilityService;

    @GET
    @Path("/{id}")
    public FacilityModel getFacilityById(@PathParam("id") Long id) {
        FacilityDto facility = facilityService.getFacilityById(id);
        return FacilityModel.of(facility);
    }


    @POST
    public FacilitySaveResponse saveFacility(@Valid FacilitySaveRequest req) {
        return facilityService.saveFacility(req);
    }

    @GET
    public List<FacilityModel> getAllFacilities() {
        return facilityService.getAllFacilities().stream().map(FacilityModel::of).toList();
    }


    @DELETE
    @Path("/{id}")
    public void deleteFacilityById(@PathParam("id") Long id) {
        facilityService.deleteFacilityById(id);
    }
}