package org.booking_hotel.countries;

import org.booking_hotel.countries.dto.CountryDto;
import org.booking_hotel.jooq.model.tables.records.CountryRecord;

import java.util.List;
import java.util.function.Consumer;

public interface CountryDao {

    List<CountryDto> getAll();

    CountryDto getById(Long id);

    Boolean existsById(Long id);

    CountryDto insert(Consumer<CountryRecord> fn);

    CountryDto updateById(Consumer<CountryRecord> fn, Long id);

    Integer deleteById(Long id);
}
