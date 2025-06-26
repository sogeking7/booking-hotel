package org.booking_hotel.daos.room_types;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.common.Page;
import org.booking_hotel.common.PageRequest;
import org.booking_hotel.daos.room_types.dto.RoomTypeDto;
import org.booking_hotel.jooq.model.tables.RoomTypes;
import org.booking_hotel.jooq.model.tables.records.RoomTypeRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;
// dsl - domain specific language, used for building SQL queries in a type-safe manner
@Dependent
public class RoomTypeDaoImpl implements RoomTypeDao {
    private final RoomTypes rt = RoomTypes.ROOM_TYPES.as("rt");
    @Inject
    DSLContext dsl;

    @Override
    public Page<RoomTypeDto> getAll(PageRequest pageRequest) {
        return null;
    }

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

        // First check if the record exists
        var existingRecord = dsl.selectFrom(rt)
                .where(rt.REMOVED.isFalse(), rt.ID.eq(id))
                .fetchOne();

        if (existingRecord == null) {
            // If record doesn't exist, insert a new one with the specified ID
            record.setId(id);
            return dsl.insertInto(rt)
                    .set(record)
                    .returning()
                    .fetchSingle(RoomTypeDto::of);
        } else {
            // If record exists, update it
            return dsl.update(rt)
                    .set(record)
                    .where(rt.REMOVED.isFalse(), rt.ID.eq(id))
                    .returning()
                    .fetchSingle(RoomTypeDto::of);
        }
    }

    @Override
    public Long removeById(Long id) {
        return (long) dsl.update(rt)
                .set(rt.REMOVED, true)
                .where(rt.REMOVED.isFalse(), rt.ID.eq(id))
                .execute();
    }

    @Override
    public List<RoomTypeDto> getByHotelId(Long hotelId) {
        return dsl.selectFrom(rt)
                .where(rt.REMOVED.isFalse(), rt.HOTEL_ID.eq(hotelId))
                .fetch().stream().map(RoomTypeDto::of).toList();
    }
}
