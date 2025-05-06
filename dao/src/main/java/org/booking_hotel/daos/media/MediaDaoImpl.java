package org.booking_hotel.daos.media;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import org.booking_hotel.daos.media.dto.MediaDto;
import org.booking_hotel.jooq.model.tables.Media;
import org.booking_hotel.jooq.model.tables.records.MediaRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.function.Consumer;

@Dependent
public class MediaDaoImpl implements MediaDao {

    private final Media m = Media.MEDIA.as("m");

    @Inject
    DSLContext dsl;

    public List<MediaDto> getAll() {
        return dsl.selectFrom(m)
                .where(m.REMOVED.isFalse())
                .fetch().stream().map(MediaDto::of).toList();
    }

    public MediaDto getById(Long id) {
        return dsl.selectFrom(m)
                .where(m.REMOVED.isFalse(), m.ID.eq(id))
                .fetchSingle(MediaDto::of);
    }

    @Override
    public MediaDto insert(Consumer<MediaRecord> fn) {
        var record = new MediaRecord();
        fn.accept(record);
        return dsl.insertInto(m)
                .set(record)
                .returning()
                .fetchSingle(MediaDto::of);
    }

    @Override
    public MediaDto updateById(Consumer<MediaRecord> fn, Long id) {
        var record = new MediaRecord();
        fn.accept(record);
        return dsl.update(m)
                .set(record)
                .where(m.REMOVED.isFalse(), m.ID.eq(id))
                .returning()
                .fetchSingle(MediaDto::of);
    }

    @Override
    public Long removeById(Long id) {
        return (long) dsl.update(m)
                .set(m.REMOVED, true)
                .where(m.REMOVED.isFalse(), m.ID.eq(id))
                .execute();
    }

    @Override
    public List<MediaDto> getByRefIdAndRefType(Long refId, String refType) {
        return dsl.selectFrom(m)
                .where(m.REMOVED.isFalse(),
                        m.REF_ID.eq(refId),
                        m.REF_TYPE.eq(refType))
                .fetch().stream().map(MediaDto::of).toList();
    }
}