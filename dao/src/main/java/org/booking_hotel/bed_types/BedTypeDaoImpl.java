package org.booking_hotel.bed_types;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.bed_types.dto.BedTypeDto;
import org.booking_hotel.jooq.model.tables.BedTypes;
import org.booking_hotel.jooq.model.tables.records.BedTypeRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class BedTypeDaoImpl implements BedTypeDao {

    private final BedTypes bt = BedTypes.BED_TYPES.as("bt");

    @Inject
    DSLContext dsl;

    public List<BedTypeDto> getAll() {
        return dsl.selectFrom(bt)
                .fetch().stream().map(BedTypeDto::of).toList();
    }

    public BedTypeDto getById(Long id) {
        return dsl.selectFrom(bt)
                .where(bt.ID.eq(id))
                .fetchSingle(BedTypeDto::of);
    }

    @Override
    public BedTypeDto insert(Consumer<BedTypeRecord> fn) {
        var record = new BedTypeRecord();
        fn.accept(record);
        return dsl.insertInto(bt)
                .set(record)
                .returning()
                .fetchSingle(BedTypeDto::of);
    }

    @Override
    public BedTypeDto updateById(Consumer<BedTypeRecord> fn, Long id) {
        var record = new BedTypeRecord();
        fn.accept(record);
        return dsl.update(bt)
                .set(record)
                .where(bt.ID.eq(id))
                .returning()
                .fetchSingle(BedTypeDto::of);
    }

    @Override
    public Integer deleteById(Long id) {
        return dsl.deleteFrom(bt)
                .where(bt.ID.eq(id))
                .execute();
    }
}