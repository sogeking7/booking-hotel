package org.booking_hotel.bed_types;

import org.booking_hotel.bed_types.dto.BedTypeDto;
import org.booking_hotel.jooq.model.tables.records.BedTypeRecord;

import java.util.List;
import java.util.function.Consumer;

public interface BedTypeDao {

    List<BedTypeDto> getAll();

    BedTypeDto getById(Long id);

    BedTypeDto insert(Consumer<BedTypeRecord> fn);

    BedTypeDto updateById(Consumer<BedTypeRecord> fn, Long id);

    Integer deleteById(Long id);
}