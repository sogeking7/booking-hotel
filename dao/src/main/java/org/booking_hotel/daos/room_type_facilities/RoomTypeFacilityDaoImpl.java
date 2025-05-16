package org.booking_hotel.daos.room_type_facilities;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.common.Page;
import org.booking_hotel.common.PageRequest;
import org.booking_hotel.daos.room_type_facilities.dto.RoomTypeFacilityDto;
import org.booking_hotel.jooq.model.tables.RoomTypeFacilities;
import org.booking_hotel.jooq.model.tables.records.RoomTypeFacilityRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class RoomTypeFacilityDaoImpl implements RoomTypeFacilityDao {

    private final RoomTypeFacilities rtf = RoomTypeFacilities.ROOM_TYPE_FACILITIES.as("rtf");
    @Inject
    DSLContext dsl;

    @Override
    public Page<RoomTypeFacilityDto> getAll(PageRequest pageRequest) {
        return null;
    }

    public List<RoomTypeFacilityDto> getAll() {
        return dsl.selectFrom(rtf)
                .where(rtf.REMOVED.isFalse())
                .fetch().stream().map(RoomTypeFacilityDto::of).toList();
    }

    public RoomTypeFacilityDto getById(Long id) {
        return dsl.selectFrom(rtf)
                .where(rtf.REMOVED.isFalse(), rtf.ID.eq(id))
                .fetchSingle(RoomTypeFacilityDto::of);
    }

    @Override
    public RoomTypeFacilityDto insert(Consumer<RoomTypeFacilityRecord> fn) {
        var record = new RoomTypeFacilityRecord();
        fn.accept(record);
        return dsl.insertInto(rtf)
                .set(record)
                .returning()
                .fetchSingle(RoomTypeFacilityDto::of);
    }

    @Override
    public RoomTypeFacilityDto updateById(Consumer<RoomTypeFacilityRecord> fn, Long id) {
        var record = new RoomTypeFacilityRecord();
        fn.accept(record);
        return dsl.update(rtf)
                .set(record)
                .where(rtf.REMOVED.isFalse(), rtf.ID.eq(id))
                .returning()
                .fetchSingle(RoomTypeFacilityDto::of);
    }

    @Override
    public Long removeById(Long id) {
        return (long) dsl.update(rtf)
                .set(rtf.REMOVED, true)
                .where(rtf.REMOVED.isFalse(), rtf.ID.eq(id))
                .execute();
    }

    @Override
    public List<RoomTypeFacilityDto> getByRoomTypeId(Long roomTypeId) {
        return dsl.selectFrom(rtf)
                .where(rtf.REMOVED.isFalse(), rtf.ROOM_TYPE_ID.eq(roomTypeId))
                .fetch().stream().map(RoomTypeFacilityDto::of).toList();
    }

    @Override
    public List<RoomTypeFacilityDto> getByFacilityId(Long facilityId) {
        return dsl.selectFrom(rtf)
                .where(rtf.REMOVED.isFalse(), rtf.FACILITY_ID.eq(facilityId))
                .fetch().stream().map(RoomTypeFacilityDto::of).toList();
    }
}