package org.booking_hotel.daos.bed_types;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.bed_types.dto.BedTypeDto;
import org.booking_hotel.jooq.model.tables.records.BedTypeRecord;

public interface BedTypeDao extends BaseDao<BedTypeDto, BedTypeRecord, Long> {

}