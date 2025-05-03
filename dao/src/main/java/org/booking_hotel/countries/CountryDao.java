package org.booking_hotel.countries;

import org.booking_hotel.countries.dto.CountryDto;
import org.booking_hotel.jooq.model.tables.records.CountrieRecord;

import java.util.List;
import java.util.function.Consumer;

public interface CountryDao {

    List<CountryDto> getAll();

    CountryDto getById(Long id);

    Boolean existsById(Long id);

    CountryDto insert(Consumer<CountrieRecord> fn);

    CountryDto updateById(Consumer<CountrieRecord> fn, Long id);

    Integer deleteById(Long id);
}
