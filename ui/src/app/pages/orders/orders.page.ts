import {Component, OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzDatePickerModule} from 'ng-zorro-antd/date-picker';
import {NzMessageService} from 'ng-zorro-antd/message';
import {NzTableModule} from 'ng-zorro-antd/table';
import {NzDividerModule} from 'ng-zorro-antd/divider';

import {OrdersService} from './OrdersService';
import {HotelsService} from '../hotels/HotelsService';
import {HotelRoomsService} from '../hotels/room-types/HotelRoomsService';
import {OrderModel, OrderSaveRequest} from '@lib/booking-hotel-api';

@Component({
  selector: 'app-orders',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzSelectModule,
    NzDatePickerModule,
    NzTableModule,
    NzDividerModule
  ],
  templateUrl: './orders.page.html',
  styleUrls: ['./orders.page.css']
})
export class OrdersPageComponent implements OnInit {
  orderForm!: FormGroup;
  orders: OrderModel[] = [];
  hotels: any[] = [];
  rooms: any[] = [];
  selectedHotelId: number | null = null;
  isLoading = false;
  userId = 1; // Default user ID, should be replaced with actual logged-in user ID

  constructor(
    private fb: FormBuilder,
    private ordersService: OrdersService,
    private hotelsService: HotelsService,
    private roomsService: HotelRoomsService,
    private route: ActivatedRoute,
    private router: Router,
    private message: NzMessageService
  ) {}

  ngOnInit(): void {
    this.initForm();
    this.loadHotels();
    this.loadOrders();

    // Check if hotelId and roomTypeId are provided in the route
    this.route.queryParams.subscribe(params => {
      if (params['hotelId']) {
        this.selectedHotelId = Number(params['hotelId']);
        this.orderForm.patchValue({ hotelId: this.selectedHotelId });
        this.onHotelChange(this.selectedHotelId).then(() => {
          // After rooms are loaded, set roomTypeId if provided
          if (params['roomTypeId']) {
            const roomTypeId = Number(params['roomTypeId']);
            this.orderForm.patchValue({ roomTypeId: roomTypeId });
          }
        });
      }
    });
  }

  initForm(): void {
    this.orderForm = this.fb.group({
      hotelId: [null, Validators.required],
      roomTypeId: [null, Validators.required],
      fromDate: [null, Validators.required],
      toDate: [null, Validators.required]
    });
  }

  async loadHotels(): Promise<void> {
    try {
      this.hotels = await this.hotelsService.getAllHotels();
    } catch (error) {
      this.message.error('Failed to load hotels');
      console.error('Error loading hotels:', error);
    }
  }

  async loadOrders(): Promise<void> {
    try {
      this.orders = await this.ordersService.getOrdersByUserId(this.userId);
    } catch (error) {
      this.message.error('Failed to load orders');
      console.error('Error loading orders:', error);
    }
  }

  async onHotelChange(hotelId: number): Promise<void> {
    if (!hotelId) return Promise.resolve();

    try {
      this.rooms = await this.roomsService.getRoomByHotelId(hotelId);
      this.orderForm.patchValue({ roomTypeId: null });
      return Promise.resolve();
    } catch (error) {
      this.message.error('Failed to load rooms');
      console.error('Error loading rooms:', error);
      return Promise.reject(error);
    }
  }

  async onSubmit(): Promise<void> {
    if (this.orderForm.invalid) {
      Object.values(this.orderForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
      return;
    }

    this.isLoading = true;

    try {
      const formValue = this.orderForm.value;

      // Format dates to ISO string
      const fromDate = new Date(formValue.fromDate).toISOString();
      const toDate = new Date(formValue.toDate).toISOString();

      const orderRequest: OrderSaveRequest = {
        hotelId: formValue.hotelId,
        roomTypeId: formValue.roomTypeId,
        fromDate: fromDate,
        toData: toDate, // Note: API has a typo in the field name
        userId: this.userId
      };

      await this.ordersService.createOrder(orderRequest);
      this.message.success('Order created successfully');
      this.orderForm.reset();
      this.loadOrders();
    } catch (error) {
      this.message.error('Failed to create order');
      console.error('Error creating order:', error);
    } finally {
      this.isLoading = false;
    }
  }

  async cancelOrder(orderId: number): Promise<void> {
    try {
      await this.ordersService.deleteOrder(orderId);
      this.message.success('Order cancelled successfully');
      this.loadOrders();
    } catch (error) {
      this.message.error('Failed to cancel order');
      console.error('Error cancelling order:', error);
    }
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString();
  }

  getHotelName(hotelId: number): string {
    const hotel = this.hotels.find(h => h.id === hotelId);
    return hotel ? hotel.name : 'Unknown Hotel';
  }

  getRoomTypeName(roomTypeId: number): string {
    const room = this.rooms.find(r => r.id === roomTypeId);
    return room ? room.type : 'Unknown Room Type';
  }
}
