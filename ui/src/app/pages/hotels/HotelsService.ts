import {Injectable} from '@angular/core';
import {firstValueFrom} from 'rxjs';
import {HotelDetailModel, HotelModel, HotelSaveRequest, HotelSaveResponse, HotelService} from '@lib/booking-hotel-api';

@Injectable({
  providedIn: 'root',
})
export class HotelsService {
  constructor(private hotelApi: HotelService) {
  }

  async getAllHotels(): Promise<HotelModel[]> {
    return await firstValueFrom(this.hotelApi.getAllHotels('body'));
  }

  async getHotelById(id: number): Promise<HotelDetailModel> {
    return await firstValueFrom(this.hotelApi.getHotelDetails(id, 'body'));
  }

  async createHotel(hotel: HotelSaveRequest): Promise<HotelSaveResponse> {
    return await firstValueFrom(this.hotelApi.saveHotel(hotel, 'body'));
  }

  async updateHotel(id: number, hotel: HotelSaveRequest): Promise<HotelSaveResponse> {
    return await firstValueFrom(this.hotelApi.saveHotel(hotel, 'body'));
  }

  async deleteHotel(id: number): Promise<void> {
    return await firstValueFrom(this.hotelApi.deleteHotelById(id, 'body'));
  }

  async searchHotels(search: string): Promise<HotelModel[]> {
    return await firstValueFrom(this.hotelApi.searchHotels(search, 'body'));
  }

  getHotels(search: string) {
    return this.hotelApi.searchHotels(search, 'body');
  }
}
