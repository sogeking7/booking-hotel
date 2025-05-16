package org.booking_hotel.common;

import jakarta.validation.constraints.NotNull;
import org.jooq.UpdatableRecord;

import java.util.List;
import java.util.function.Consumer;

public interface BaseDao<T, R extends UpdatableRecord, ID> {
    List<T> getAll();

    @NotNull
    T getById(@NotNull ID id);

    @NotNull
    T insert(Consumer<R> fn);

    @NotNull
    T updateById(Consumer<R> fn, @NotNull ID id);

    @NotNull
    ID removeById(@NotNull ID id);

    Page<T> getAll(PageRequest pageRequest);
}
