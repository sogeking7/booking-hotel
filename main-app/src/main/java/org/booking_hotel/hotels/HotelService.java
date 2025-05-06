package org.booking_hotel.hotels;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.cities.CityDao;
import org.booking_hotel.hotels.dto.HotelDto;
import org.booking_hotel.hotels.model.HotelSaveRequest;
import org.booking_hotel.hotels.model.HotelSaveResponse;
import org.booking_hotel.jooq.model.tables.records.HotelRecord;
import org.booking_hotel.utils.BusinessException;

import java.util.List;
import java.util.function.Consumer;

@RequestScoped
@Transactional
public class HotelService {
    @Inject
    HotelDao hotelDao;

    @Inject
    CityDao cityDao;

    public List<HotelDto> getAllHotels() {
        return hotelDao.getAll();
    }

    public HotelDto getHotelById(Long id) {
        return hotelDao.getById(id);
    }

    public HotelSaveResponse saveHotel(HotelSaveRequest req) throws BusinessException {
        if (req.cityId() != null) {
            try {
                cityDao.getById(req.cityId());
            } catch (Exception e) {
                throw new BusinessException(
                        Response.Status.BAD_REQUEST.getStatusCode(),
                        "hotel.save.cityIsNotExists",
                        "City with id " + req.cityId() + " is not exists"
                );
            }
        }

        Consumer<HotelRecord> fn = record -> {
            record.setName(req.name());
            record.setAddress(req.address());
            record.setPhone(req.phone());
            record.setCityId(req.cityId());
        };

        HotelDto createdHotel = req.id() == null ? hotelDao.insert(fn) : hotelDao.updateById(fn, req.id());

        return new HotelSaveResponse(
                createdHotel.id(),
                createdHotel.name(),
                createdHotel.address(),
                createdHotel.phone(),
                createdHotel.cityId()
        );
    }

    public void deleteHotelById(Long id) {
        hotelDao.deleteById(id);
    }
}
