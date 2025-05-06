package org.booking_hotel.media;

import org.booking_hotel.media.dto.MediaDto;
import org.booking_hotel.jooq.model.tables.records.MediaRecord;

import java.util.List;
import java.util.function.Consumer;

public interface MediaDao {

    List<MediaDto> getAll();

    MediaDto getById(Long id);

    MediaDto insert(Consumer<MediaRecord> fn);

    MediaDto updateById(Consumer<MediaRecord> fn, Long id);

    Integer deleteById(Long id);
    
    List<MediaDto> getByRefIdAndRefType(Long refId, String refType);
}