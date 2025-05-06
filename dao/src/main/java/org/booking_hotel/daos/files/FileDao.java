package org.booking_hotel.daos.files;

import org.booking_hotel.common.BaseDao;
import org.booking_hotel.daos.files.dto.FileDto;
import org.booking_hotel.jooq.model.tables.records.FileRecord;

public interface FileDao extends BaseDao<FileDto, FileRecord, Long> {
}