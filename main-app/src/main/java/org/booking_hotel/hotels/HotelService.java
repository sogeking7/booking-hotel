package org.booking_hotel.hotels;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.booking_hotel.daos.bed_types.BedTypeDao;
import org.booking_hotel.daos.bed_types.dto.BedTypeDto;
import org.booking_hotel.daos.cities.CityDao;
import org.booking_hotel.daos.facilities.FacilityDao;
import org.booking_hotel.daos.facilities.dto.FacilityDto;
import org.booking_hotel.daos.hotels.HotelDao;
import org.booking_hotel.daos.hotels.dto.HotelDto;
import org.booking_hotel.daos.room_type_facilities.RoomTypeFacilityDao;
import org.booking_hotel.daos.room_type_facilities.dto.RoomTypeFacilityDto;
import org.booking_hotel.daos.room_types.RoomTypeDao;
import org.booking_hotel.daos.room_types.dto.RoomTypeDto;
import org.booking_hotel.hotels.model.*;
import org.booking_hotel.jooq.model.tables.Hotels;
import org.booking_hotel.jooq.model.tables.records.HotelRecord;
import org.booking_hotel.utils.BusinessException;
import org.jooq.DSLContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RequestScoped
@Transactional
public class HotelService {
    @Inject
    HotelDao hotelDao;

    @Inject
    CityDao cityDao;

    @Inject
    RoomTypeDao roomTypeDao;

    @Inject
    BedTypeDao bedTypeDao;

    @Inject
    FacilityDao facilityDao;

    @Inject
    RoomTypeFacilityDao roomTypeFacilityDao;

    @Inject
    DSLContext ctx;

    public List<HotelDto> getAllHotels() {
        return hotelDao.getAll();
    }


    public List<HotelDto> searchHotels(String search) {
        if (search == null || search.isBlank()) {
            return ctx.selectFrom(Hotels.HOTELS)
                    .fetchInto(HotelDto.class);
        }
        return ctx.selectFrom(Hotels.HOTELS)
                .where(Hotels.HOTELS.NAME.likeIgnoreCase("%" + search + "%"))
                .fetchInto(HotelDto.class);
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
        hotelDao.removeById(id);
    }

    public HotelDetailModel getHotelDetails(Long id) {
        // Get hotel
        HotelDto hotelDto = hotelDao.getById(id);

        // Get room types for hotel
        List<RoomTypeDto> roomTypeDtos = roomTypeDao.getByHotelId(id);

        // Get bed types for all room types
        Map<Long, BedTypeDto> bedTypeDtoMap = roomTypeDtos.stream()
                .map(RoomTypeDto::bedTypeId)
                .distinct()
                .collect(Collectors.toMap(
                        bedTypeId -> bedTypeId,
                        bedTypeDao::getById
                ));

        // Get room type facilities for all room types
        Map<Long, List<RoomTypeFacilityDto>> roomTypeFacilitiesMap = roomTypeDtos.stream()
                .map(RoomTypeDto::id)
                .collect(Collectors.toMap(
                        roomTypeId -> roomTypeId,
                        roomTypeId -> roomTypeFacilityDao.getByRoomTypeId(roomTypeId)
                ));

        // Get facilities for all room type facilities
        Map<Long, FacilityDto> facilityDtoMap = roomTypeFacilitiesMap.values().stream()
                .flatMap(List::stream)
                .map(RoomTypeFacilityDto::facilityId)
                .distinct()
                .collect(Collectors.toMap(
                        facilityId -> facilityId,
                        facilityDao::getById
                ));

        // Create room type models with bed types and facilities
        List<RoomTypeModel> roomTypeModels = roomTypeDtos.stream()
                .map(roomTypeDto -> {
                    BedTypeModel bedTypeModel = BedTypeModel.of(bedTypeDtoMap.get(roomTypeDto.bedTypeId()));

                    List<FacilityModel> facilityModels = roomTypeFacilitiesMap.getOrDefault(roomTypeDto.id(), new ArrayList<>())
                            .stream()
                            .map(rtf -> FacilityModel.of(facilityDtoMap.get(rtf.facilityId())))
                            .toList();

                    return RoomTypeModel.of(roomTypeDto, bedTypeModel, facilityModels);
                })
                .toList();

        // Create hotel detail model
        return HotelDetailModel.of(hotelDto, roomTypeModels);
    }
}
