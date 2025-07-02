package org.booking_hotel.daos.countries;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.countries.dto.CountryDto;
import org.booking_hotel.jooq.model.tables.records.CountryRecord;
import java.util.function.Consumer;


public interface CountryDao extends BaseDao<CountryDto, CountryRecord, Long> {

    Boolean existsById(Long id);
    Boolean existsByNameIgnoreCase(String name);

    @Override
    CountryDto updateById(Consumer<CountryRecord> fn, Long id);
}
