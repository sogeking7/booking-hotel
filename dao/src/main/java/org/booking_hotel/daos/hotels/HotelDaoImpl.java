package org.booking_hotel.daos.hotels;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.common.Page;
import org.booking_hotel.common.PageRequest;
import org.booking_hotel.daos.hotels.dto.HotelDto;
import org.booking_hotel.jooq.model.tables.Hotels;
import org.booking_hotel.jooq.model.tables.records.HotelRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class HotelDaoImpl implements HotelDao {
    private final Hotels h = Hotels.HOTELS.as("h");
    @Inject
    DSLContext dsl;

    @Override
    public Page<HotelDto> getAll(PageRequest pageRequest) {
        return null;
    }

    public List<HotelDto> getAll() {
        return dsl.selectFrom(h)
                .where(h.REMOVED.isFalse())
                .fetch().stream().map(HotelDto::of).toList();
    }

    public HotelDto getById(Long id) {
        return dsl.selectFrom(h)
                .where(h.REMOVED.isFalse(), h.ID.eq(id))
                .fetchSingle(HotelDto::of);
    }

    @Override
    public HotelDto insert(Consumer<HotelRecord> fn) {
        var record = new HotelRecord();
        fn.accept(record);
        return dsl.insertInto(h)
                .set(record)
                .returning()
                .fetchSingle(HotelDto::of);
    }

    @Override
    public HotelDto updateById(Consumer<HotelRecord> fn, Long id) {
        var record = new HotelRecord();
        fn.accept(record);
        return dsl.update(h)
                .set(record)
                .where(h.REMOVED.isFalse(), h.ID.eq(id))
                .returning()
                .fetchSingle(HotelDto::of);
    }

    @Override
    public Long removeById(Long id) {
        return (long) dsl.update(h)
                .set(h.REMOVED, true)
                .where(h.REMOVED.isFalse(), h.ID.eq(id))
                .execute();
    }

    @Override
    public Boolean existsById(Long id) {
        return dsl.fetchExists(
                dsl.selectFrom(h)
                        .where(h.REMOVED.isFalse(), h.ID.eq(id))
        );
    }
}