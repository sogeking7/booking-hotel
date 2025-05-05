package org.booking_hotel.countries;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.booking_hotel.countries.dto.CountryDto;
import org.booking_hotel.countries.model.CountrySaveRequest;
import org.booking_hotel.countries.model.CountrySaveResponse;
import org.booking_hotel.jooq.model.tables.records.CountryRecord;
import org.booking_hotel.utils.BusinessException;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class CountryService {
    @Inject
    CountryDao countryDao;

    public List<CountryDto> getAllCountries() {
        return countryDao.getAll();
    }

    public CountryDto getCountryById(Long id) {
        return countryDao.getById(id);
    }

    public CountrySaveResponse saveCountry(CountrySaveRequest req) throws BusinessException {
        Consumer<CountryRecord> fn = record -> {
            record.setCode(req.code());
            record.setCurrency(req.currency());
            record.setName(req.name());
        };

        CountryDto createdCountry = req.id() == null ? countryDao.insert(fn) : countryDao.updateById(fn, req.id());

        return new CountrySaveResponse(createdCountry.id());
    }

    public void deleteCountryById(Long id) {
        countryDao.deleteById(id);
    }
}
