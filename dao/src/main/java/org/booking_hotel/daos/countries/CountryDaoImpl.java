package org.booking_hotel.daos.countries;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.common.Page;
import org.booking_hotel.common.PageRequest;
import org.booking_hotel.daos.countries.dto.CountryDto;
import org.booking_hotel.jooq.model.tables.Countries;
import org.booking_hotel.jooq.model.tables.records.CountryRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class CountryDaoImpl implements CountryDao {
    private final Countries c = Countries.COUNTRIES.as("c");

    @Inject
    DSLContext dsl;

    @Override
    public Page<CountryDto> getAll(PageRequest pageRequest) {
        return null;
    }

    public List<CountryDto> getAll() {
        return dsl.selectFrom(c)
                .where(c.REMOVED.isFalse())
                .fetch().stream().map(CountryDto::of).toList();
    }

    public CountryDto getById(Long id) {
        return dsl.selectFrom(c)
                .where(c.REMOVED.isFalse(), c.ID.eq(id))
                .fetchSingle(CountryDto::of);

    }

    @Override
    public Boolean existsById(Long id) {
        return dsl.fetchExists(c, c.ID.eq(id), c.REMOVED.isFalse());
    }

    @Override
    public CountryDto insert(Consumer<CountryRecord> fn) {
        var record = new CountryRecord();
        fn.accept(record);
        return dsl.insertInto(c)
                .set(record)
                .returning()
                .fetchSingle(CountryDto::of);
    }

    @Override
    public CountryDto updateById(Consumer<CountryRecord> fn, Long id) {
        var record = new CountryRecord();
        fn.accept(record);
        return dsl.update(c)
                .set(record)
                .where(c.REMOVED.isFalse(), c.ID.eq(id))
                .returning()
                .fetchSingle(CountryDto::of);
    }

    @Override
    public Long removeById(Long id) {
        return (long) dsl.update(c)
                .set(c.REMOVED, true)
                .where(c.REMOVED.isFalse(), c.ID.eq(id))
                .execute();
    }
}
