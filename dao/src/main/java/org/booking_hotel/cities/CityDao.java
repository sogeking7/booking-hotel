package org.booking_hotel.cities;

import org.booking_hotel.cities.dto.CityDto;
import org.booking_hotel.jooq.model.tables.records.CityRecord;

import java.util.List;
import java.util.function.Consumer;

public interface CityDao {

    List<CityDto> getAll();

    CityDto getById(Long id);

    CityDto insert(Consumer<CityRecord> fn);

    CityDto updateById(Consumer<CityRecord> fn, Long id);

    Integer deleteById(Long id);
}
