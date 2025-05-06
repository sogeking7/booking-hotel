package org.booking_hotel.files;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.files.dto.FileDto;
import org.booking_hotel.jooq.model.tables.Files;
import org.booking_hotel.jooq.model.tables.records.FileRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class FileDaoImpl implements FileDao {

    private final Files f = Files.FILES.as("f");

    @Inject
    DSLContext dsl;

    public List<FileDto> getAll() {
        return dsl.selectFrom(f)
                .where(f.REMOVED.isFalse())
                .fetch().stream().map(FileDto::of).toList();
    }

    public FileDto getById(Long id) {
        return dsl.selectFrom(f)
                .where(f.REMOVED.isFalse(), f.ID.eq(id))
                .fetchSingle(FileDto::of);
    }

    @Override
    public FileDto insert(Consumer<FileRecord> fn) {
        var record = new FileRecord();
        fn.accept(record);
        return dsl.insertInto(f)
                .set(record)
                .returning()
                .fetchSingle(FileDto::of);
    }

    @Override
    public FileDto updateById(Consumer<FileRecord> fn, Long id) {
        var record = new FileRecord();
        fn.accept(record);
        return dsl.update(f)
                .set(record)
                .where(f.REMOVED.isFalse(), f.ID.eq(id))
                .returning()
                .fetchSingle(FileDto::of);
    }

    @Override
    public Integer deleteById(Long id) {
        return dsl.update(f)
                .set(f.REMOVED, true)
                .where(f.REMOVED.isFalse(), f.ID.eq(id))
                .execute();
    }
}