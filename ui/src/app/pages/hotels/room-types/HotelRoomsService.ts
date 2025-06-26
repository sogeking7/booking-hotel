import {Injectable} from '@angular/core';
import {catchError, firstValueFrom} from 'rxjs';
import {RoomTypeService} from '../../../../lib/booking-hotel-api';
import {RoomTypeSaveRequest} from '../../../../lib/booking-hotel-api';
import {RoomTypeModel} from '../../../../lib/booking-hotel-api';

export interface Room {
  id: number;
  type: string;
  price: number;
  bedTypeId?: number;
  description: string;
}

@Injectable({
  providedIn: 'root',
})

export class HotelRoomsService {
  constructor(private roomTypeService: RoomTypeService) {
  }

  async getRoomByHotelId(hotelId: number): Promise<Room[]> {
    try {
      const roomTypes = await firstValueFrom(
        this.roomTypeService.getAllRoomTypes('body', false, {
            httpHeaderAccept: 'application/json',
          }
        )
      );
      // Filter room types by hotel ID
      const filteredRoomTypes = roomTypes.filter(roomType => roomType.hotelId === hotelId);
      return filteredRoomTypes.map((roomType: RoomTypeModel) => ({
        id: roomType.id,
        type: roomType.name,
        price: (roomType.count ?? 0) * 1000,
        bedTypeId: roomType.bedTypeId!,
        description: this.createRoomDescription(roomType),
      }));
    } catch (error) {
      console.error('Error fetching rooms:', error);
      throw error;
    }
  }

  async getRoomById(hotelId: number, roomId: number): Promise<Room> {
    try {
      const roomTypes = await firstValueFrom(
        this.roomTypeService.getRoomTypeById(roomId, 'body', false, {
          httpHeaderAccept: 'application/json',

        })
      );
      if (roomTypes.hotelId !== hotelId) {
        throw new Error('Room does not belong to the hotel');
      }

      return {
        id: roomTypes.id!,
        type: roomTypes.name!,
        price: (roomTypes.count ?? 0) * 1000,
        bedTypeId: roomTypes.bedTypeId!,
        description: this.createRoomDescription(roomTypes),
      };
    } catch (error) {
      console.error('Error fetching room by ID:', error);
      throw error;
    }
  }

  private createRoomDescription(roomType: RoomTypeModel): string {
    let desc = roomType.bedType?.name ? `Bed: ${roomType.bedType.name}` : '';

    if (roomType.facilities?.length) {
      const facs = roomType.facilities.map(f => f.name).join(', ');
      desc += desc ? `, Facilities: ${facs}` : `Facilities: ${facs}`;
    }

    return desc || 'No description';
  }


  async saveRoom(hotelId: number, room: Room): Promise<any> {
    const request: RoomTypeSaveRequest = {
      id: room.id ?? undefined,
      name: room.type,
      hotelId,
      bedTypeId: room.bedTypeId ?? 1, // Default to bedTypeId 1 if not provided
      count: Math.floor(room.price / 1000),
    };
    try {
      console.log('🔍 Отправка в backend:', request);
      return await firstValueFrom(
        this.roomTypeService.saveRoomType(request, 'body', false, {
          httpHeaderAccept: 'application/json',
        })
      );
    } catch (error) {
      console.error('Error saving room:', error);
      throw error;
    }
  }

  async deleteRoom(hotelId: number, roomId: number): Promise<void> {
    try {
      const room = await this.getRoomById(hotelId, roomId);
      if (room) {
        await firstValueFrom(
          this.roomTypeService.deleteRoomTypeById(roomId, 'body', false,));
      }
    } catch
      (error)
      {
        console.error('Error deleting room:', error);
        throw error;
      }
    }
  }
