package org.booking_hotel.daos.cities;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.daos.cities.dto.CityDto;
import org.booking_hotel.jooq.model.tables.Cities;
import org.booking_hotel.jooq.model.tables.records.CityRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class CityDaoImpl implements CityDao {

    private final Cities c = Cities.CITIES.as("c");

    @Inject
    DSLContext dsl;

    public List<CityDto> getAll() {
        return dsl.selectFrom(c)
                .where(c.REMOVED.isFalse())
                .fetch().stream().map(CityDto::of).toList();
    }

    public CityDto getById(Long id) {
        return dsl.selectFrom(c)
                .where(c.REMOVED.isFalse(), c.ID.eq(id))
                .fetchSingle(CityDto::of);

    }

    @Override
    public CityDto insert(Consumer<CityRecord> fn) {
        var record = new CityRecord();
        fn.accept(record);
        return dsl.insertInto(c)
                .set(record)
                .returning()
                .fetchSingle(CityDto::of);
    }

    @Override
    public CityDto updateById(Consumer<CityRecord> fn, Long id) {
        var record = new CityRecord();
        fn.accept(record);
        return dsl.update(c)
                .set(record)
                .where(c.REMOVED.isFalse(), c.ID.eq(id))
                .returning()
                .fetchSingle(CityDto::of);
    }

    @Override
    public Long removeById(Long id) {
        return (long) dsl.update(c)
                .set(c.REMOVED, true)
                .where(c.REMOVED.isFalse(), c.ID.eq(id))
                .execute();
    }
}
