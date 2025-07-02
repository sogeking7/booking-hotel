import {Injectable} from '@angular/core';
import {firstValueFrom} from 'rxjs';
import {OrderModel, OrderSaveRequest, OrderSaveResponse, OrderService} from '@lib/booking-hotel-api';

@Injectable({
  providedIn: 'root',
})
export class OrdersService {
  constructor(private orderApi: OrderService) {}

  async getAllOrders(): Promise<OrderModel[]> {
    return await firstValueFrom(this.orderApi.getAllOrders('body'));
  }

  async getOrderById(id: number): Promise<OrderModel> {
    return await firstValueFrom(this.orderApi.getOrderById(id, 'body'));
  }

  async getOrdersByHotelId(hotelId: number): Promise<OrderModel[]> {
    return await firstValueFrom(this.orderApi.getOrdersByHotelId(hotelId, 'body'));
  }

  async getOrdersByRoomTypeId(roomTypeId: number): Promise<OrderModel[]> {
    return await firstValueFrom(this.orderApi.getOrdersByRoomTypeId(roomTypeId, 'body'));
  }

  async getOrdersByUserId(userId: number): Promise<OrderModel[]> {
    return await firstValueFrom(this.orderApi.getOrdersByUserId(userId, 'body'));
  }

  async createOrder(order: OrderSaveRequest): Promise<OrderSaveResponse> {
    return await firstValueFrom(this.orderApi.saveOrder(order, 'body'));
  }

  async deleteOrder(id: number): Promise<void> {
    return await firstValueFrom(this.orderApi.deleteOrderById(id, 'body'));
  }
}
