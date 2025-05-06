package org.booking_hotel.files;

import org.booking_hotel.files.dto.FileDto;
import org.booking_hotel.jooq.model.tables.records.FileRecord;

import java.util.List;
import java.util.function.Consumer;

public interface FileDao {

    List<FileDto> getAll();

    FileDto getById(Long id);

    FileDto insert(Consumer<FileRecord> fn);

    FileDto updateById(Consumer<FileRecord> fn, Long id);

    Integer deleteById(Long id);
}