package org.booking_hotel.cities;

import org.booking_hotel.cities.dto.CityDto;
import org.booking_hotel.jooq.model.tables.records.CitieRecord;

import java.util.List;
import java.util.function.Consumer;

public interface CityDao {

    List<CityDto> getAll();

    CityDto getById(Long id);
    
    CityDto insert(Consumer<CitieRecord> fn);

    CityDto updateById(Consumer<CitieRecord> fn, Long id);

    Integer deleteById(Long id);
}
