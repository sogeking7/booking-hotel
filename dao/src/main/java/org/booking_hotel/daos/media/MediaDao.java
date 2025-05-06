package org.booking_hotel.daos.media;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.media.dto.MediaDto;
import org.booking_hotel.jooq.model.tables.records.MediaRecord;

import java.util.List;

public interface MediaDao extends BaseDao<MediaDto, MediaRecord, Long> {

    List<MediaDto> getByRefIdAndRefType(Long refId, String refType);
}