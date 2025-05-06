package org.booking_hotel.daos.facilities;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.facilities.dto.FacilityDto;
import org.booking_hotel.jooq.model.tables.records.FacilityRecord;

public interface FacilityDao extends BaseDao<FacilityDto, FacilityRecord, Long> {

}