package org.booking_hotel.facilities;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.booking_hotel.daos.facilities.FacilityDao;
import org.booking_hotel.daos.facilities.dto.FacilityDto;
import org.booking_hotel.facilities.model.FacilitySaveRequest;
import org.booking_hotel.facilities.model.FacilitySaveResponse;
import org.booking_hotel.jooq.model.tables.records.FacilityRecord;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class FacilityService {
    @Inject
    FacilityDao facilityDao;

    public List<FacilityDto> getAllFacilities() {
        return facilityDao.getAll();
    }

    public FacilityDto getFacilityById(Long id) {
        return facilityDao.getById(id);
    }

    public FacilitySaveResponse saveFacility(FacilitySaveRequest req) {
        Consumer<FacilityRecord> fn = record -> {
            record.setName(req.name());
            record.setIconRef(req.iconRef());
            record.setType(req.type());
        };

        FacilityDto createdFacility = req.id() == null ? facilityDao.insert(fn) : facilityDao.updateById(fn, req.id());

        return new FacilitySaveResponse(
                createdFacility.id(),
                createdFacility.name(),
                createdFacility.iconRef(),
                createdFacility.type()
        );
    }

    public void deleteFacilityById(Long id) {
        facilityDao.removeById(id);
    }
}