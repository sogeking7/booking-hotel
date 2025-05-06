package org.booking_hotel.daos.room_types;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.daos.room_types.dto.RoomTypeDto;
import org.booking_hotel.jooq.model.tables.RoomTypes;
import org.booking_hotel.jooq.model.tables.records.RoomTypeRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class RoomTypeDaoImpl implements RoomTypeDao {

    private final RoomTypes rt = RoomTypes.ROOM_TYPES.as("rt");

    @Inject
    DSLContext dsl;

    public List<RoomTypeDto> getAll() {
        return dsl.selectFrom(rt)
                .where(rt.REMOVED.isFalse())
                .fetch().stream().map(RoomTypeDto::of).toList();
    }

    public RoomTypeDto getById(Long id) {
        return dsl.selectFrom(rt)
                .where(rt.REMOVED.isFalse(), rt.ID.eq(id))
                .fetchSingle(RoomTypeDto::of);
    }

    @Override
    public RoomTypeDto insert(Consumer<RoomTypeRecord> fn) {
        var record = new RoomTypeRecord();
        fn.accept(record);
        return dsl.insertInto(rt)
                .set(record)
                .returning()
                .fetchSingle(RoomTypeDto::of);
    }

    @Override
    public RoomTypeDto updateById(Consumer<RoomTypeRecord> fn, Long id) {
        var record = new RoomTypeRecord();
        fn.accept(record);
        return dsl.update(rt)
                .set(record)
                .where(rt.REMOVED.isFalse(), rt.ID.eq(id))
                .returning()
                .fetchSingle(RoomTypeDto::of);
    }

    @Override
    public Long removeById(Long id) {
        return (long) dsl.update(rt)
                .set(rt.REMOVED, true)
                .where(rt.REMOVED.isFalse(), rt.ID.eq(id))
                .execute();
    }
}