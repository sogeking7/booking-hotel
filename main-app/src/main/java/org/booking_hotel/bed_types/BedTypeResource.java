package org.booking_hotel.bed_types;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.bed_types.dto.BedTypeDto;
import org.booking_hotel.bed_types.model.BedTypeModel;
import org.booking_hotel.bed_types.model.BedTypeSaveRequest;
import org.booking_hotel.bed_types.model.BedTypeSaveResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/bed-types")
@Tag(name = "BedType", description = "Operations related to Bed Type items")
public class BedTypeResource {
    @Inject
    BedTypeService bedTypeService;

    @GET
    @Path("/{id}")
    public BedTypeModel getBedTypeById(@PathParam("id") Long id) {
        BedTypeDto bedType = bedTypeService.getBedTypeById(id);
        return BedTypeModel.of(bedType);
    }

    @POST
    public BedTypeSaveResponse saveBedType(@Valid BedTypeSaveRequest req) {
        return bedTypeService.saveBedType(req);
    }

    @GET
    public List<BedTypeModel> getAllBedTypes() {
        return bedTypeService.getAllBedTypes().stream().map(BedTypeModel::of).toList();
    }

    @DELETE
    @Path("/{id}")
    public void deleteBedTypeById(@PathParam("id") Long id) {
        bedTypeService.deleteBedTypeById(id);
    }
}