package org.booking_hotel.countries;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.countries.dto.CountryDto;
import org.booking_hotel.jooq.model.tables.Countries;
import org.booking_hotel.jooq.model.tables.records.CountrieRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class CountryDaoImpl implements CountryDao {
    private final Countries c = Countries.COUNTRIES.as("c");

    @Inject
    DSLContext dsl;

    public List<CountryDto> getAll() {
        return dsl.selectFrom(c)
                .fetch().stream().map(CountryDto::of).toList();
    }

    public CountryDto getById(Long id) {
        return dsl.selectFrom(c)
                .where(c.ID.eq(id))
                .fetchSingle(CountryDto::of);

    }

    @Override
    public Boolean existsById(Long id) {
        return dsl.fetchExists(c, c.ID.eq(id));
    }

    @Override
    public CountryDto insert(Consumer<CountrieRecord> fn) {
        var record = new CountrieRecord();
        fn.accept(record);
        return dsl.insertInto(c)
                .set(record)
                .returning()
                .fetchSingle(CountryDto::of);
    }

    @Override
    public CountryDto updateById(Consumer<CountrieRecord> fn, Long id) {
        var record = new CountrieRecord();
        fn.accept(record);
        return dsl.update(c)
                .set(record)
                .where(c.ID.eq(id))
                .returning()
                .fetchSingle(CountryDto::of);
    }

    @Override
    public Integer deleteById(Long id) {
        return dsl.deleteFrom(c)
                .where(c.ID.eq(id))
                .execute();
    }
}
