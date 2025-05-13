package org.booking_hotel.cities;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.cities.model.CitySaveRequest;
import org.booking_hotel.cities.model.CitySaveResponse;
import org.booking_hotel.daos.cities.CityDao;
import org.booking_hotel.daos.cities.dto.CityDto;
import org.booking_hotel.daos.countries.CountryDao;
import org.booking_hotel.jooq.model.tables.records.CityRecord;
import org.booking_hotel.utils.BusinessException;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class CityService {
    @Inject
    CityDao cityDao;

    @Inject
    CountryDao countryDao;

    public List<CityDto> getAllCities() {
        return cityDao.getAll();
    }

    public CityDto getCityById(Long id) {
        return cityDao.getById(id);
    }

    @RolesAllowed("admin")
    public CitySaveResponse saveCity(CitySaveRequest req) throws BusinessException {
        Boolean isCountryExists = countryDao.existsById(req.countryId());

        if (!isCountryExists) {
            throw new BusinessException(
                    Response.Status.BAD_REQUEST.getStatusCode(),
                    "city.save.countryIsNotExists",
                    "Country with id " + req.countryId() + " is not exists"
            );
        }

        Consumer<CityRecord> fn = record -> {
            record.setName(req.name());
            record.setCountryId(req.countryId());
        };

        CityDto createdCity = req.id() == null ? cityDao.insert(fn) : cityDao.updateById(fn, req.id());

        return new CitySaveResponse(createdCity.id(), createdCity.countryId());
    }

    @RolesAllowed("admin")
    public void deleteCityById(Long id) {
        cityDao.removeById(id);
    }
}
