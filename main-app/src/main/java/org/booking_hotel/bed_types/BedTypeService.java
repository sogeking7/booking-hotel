package org.booking_hotel.bed_types;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.booking_hotel.bed_types.dto.BedTypeDto;
import org.booking_hotel.bed_types.model.BedTypeSaveRequest;
import org.booking_hotel.bed_types.model.BedTypeSaveResponse;
import org.booking_hotel.jooq.model.tables.records.BedTypeRecord;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class BedTypeService {
    @Inject
    BedTypeDao bedTypeDao;

    public List<BedTypeDto> getAllBedTypes() {
        return bedTypeDao.getAll();
    }

    public BedTypeDto getBedTypeById(Long id) {
        return bedTypeDao.getById(id);
    }

    public BedTypeSaveResponse saveBedType(BedTypeSaveRequest req) {
        Consumer<BedTypeRecord> fn = record -> {
            record.setName(req.name());
            record.setIconRef(req.iconRef());
        };

        BedTypeDto createdBedType = req.id() == null ? bedTypeDao.insert(fn) : bedTypeDao.updateById(fn, req.id());

        return new BedTypeSaveResponse(createdBedType.id());
    }

    public void deleteBedTypeById(Long id) {
        bedTypeDao.deleteById(id);
    }
}