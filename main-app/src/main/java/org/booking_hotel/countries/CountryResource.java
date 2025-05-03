package org.booking_hotel.countries;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import org.booking_hotel.countries.dto.CountryDto;
import org.booking_hotel.countries.model.CountryModel;
import org.booking_hotel.countries.model.CountrySaveRequest;
import org.booking_hotel.countries.model.CountrySaveResponse;
import org.booking_hotel.utils.BusinessException;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;

@Path("/core/countries")
@Tag(name = "Country", description = "Operations related to Country items")
public class CountryResource {
    @Inject
    CountryService countryService;

    @GET
    @Path("/{id}")
    public CountryModel getCountryById(@PathParam("id") Long id) {
        CountryDto country = countryService.getCountryById(id);
        return CountryModel.of(country);
    }

    @POST
    public CountrySaveResponse saveCountry(@Valid CountrySaveRequest req) throws BusinessException {
        return countryService.saveCountry(req);
    }

    @GET
    public List<CountryModel> getAllCountries() {
        return countryService.getAllCountries().stream().map(CountryModel::of).toList();
    }

    @DELETE
    @Path("/{id}")
    public void deleteCountryById(@PathParam("id") Long id) {
        countryService.deleteCountryById(id);
    }
}
