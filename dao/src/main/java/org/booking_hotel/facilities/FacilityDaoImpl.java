package org.booking_hotel.facilities;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.facilities.dto.FacilityDto;
import org.booking_hotel.jooq.model.tables.Facilities;
import org.booking_hotel.jooq.model.tables.records.FacilityRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class FacilityDaoImpl implements FacilityDao {

    private final Facilities f = Facilities.FACILITIES.as("f");

    @Inject
    DSLContext dsl;

    public List<FacilityDto> getAll() {
        return dsl.selectFrom(f)
                .where(f.REMOVED.isFalse())
                .fetch().stream().map(FacilityDto::of).toList();
    }

    public FacilityDto getById(Long id) {
        return dsl.selectFrom(f)
                .where(f.REMOVED.isFalse(), f.ID.eq(id))
                .fetchSingle(FacilityDto::of);
    }

    @Override
    public FacilityDto insert(Consumer<FacilityRecord> fn) {
        var record = new FacilityRecord();
        fn.accept(record);
        return dsl.insertInto(f)
                .set(record)
                .returning()
                .fetchSingle(FacilityDto::of);
    }

    @Override
    public FacilityDto updateById(Consumer<FacilityRecord> fn, Long id) {
        var record = new FacilityRecord();
        fn.accept(record);
        return dsl.update(f)
                .set(record)
                .where(f.REMOVED.isFalse(), f.ID.eq(id))
                .returning()
                .fetchSingle(FacilityDto::of);
    }

    @Override
    public Integer deleteById(Long id) {
        return dsl.update(f)
                .set(f.REMOVED, true)
                .where(f.REMOVED.isFalse(), f.ID.eq(id))
                .execute();
    }
}