package org.booking_hotel.daos.countries;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.countries.dto.CountryDto;
import org.booking_hotel.jooq.model.tables.records.CountryRecord;

public interface CountryDao extends BaseDao<CountryDto, CountryRecord, Long> {

    Boolean existsById(Long id);
}
