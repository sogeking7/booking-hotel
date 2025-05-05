package org.booking_hotel.facilities;

import org.booking_hotel.facilities.dto.FacilityDto;
import org.booking_hotel.jooq.model.tables.records.FacilityRecord;

import java.util.List;
import java.util.function.Consumer;

public interface FacilityDao {

    List<FacilityDto> getAll();

    FacilityDto getById(Long id);

    FacilityDto insert(Consumer<FacilityRecord> fn);

    FacilityDto updateById(Consumer<FacilityRecord> fn, Long id);

    Integer deleteById(Long id);
}