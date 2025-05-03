package org.booking_hotel.cities;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.cities.dto.CityDto;
import org.booking_hotel.jooq.model.tables.Cities;
import org.booking_hotel.jooq.model.tables.records.CitieRecord;
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
                .fetch().stream().map(CityDto::of).toList();
    }

    public CityDto getById(Long id) {
        return dsl.selectFrom(c)
                .where(c.ID.eq(id))
                .fetchSingle(CityDto::of);

    }

    @Override
    public CityDto insert(Consumer<CitieRecord> fn) {
        var record = new CitieRecord();
        fn.accept(record);
        return dsl.insertInto(c)
                .set(record)
                .returning()
                .fetchSingle(CityDto::of);
    }

    @Override
    public CityDto updateById(Consumer<CitieRecord> fn, Long id) {
        var record = new CitieRecord();
        fn.accept(record);
        return dsl.update(c)
                .set(record)
                .where(c.ID.eq(id))
                .returning()
                .fetchSingle(CityDto::of);
    }

    @Override
    public Integer deleteById(Long id) {
        return dsl.deleteFrom(c)
                .where(c.ID.eq(id))
                .execute();
    }
}
