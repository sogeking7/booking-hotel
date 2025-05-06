package org.booking_hotel.daos.cities;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.cities.dto.CityDto;
import org.booking_hotel.jooq.model.tables.records.CityRecord;

public interface CityDao extends BaseDao<CityDto, CityRecord, Long> {
}
