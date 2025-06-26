import { Component, inject, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { CommonModule } from '@angular/common';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { HttpErrorResponse } from '@angular/common/http';
import { HotelsService } from '../HotelsService';
import {HotelDetailModel} from '@lib/booking-hotel-api';

@Component({
  selector: 'app-hotel-detail-page',
  imports: [
    CommonModule,
    NzSpinModule,
    NzIconModule,
    NzTypographyModule,
    NzCardModule,
    NzTableModule,
    NzTagModule,
    NzButtonModule,
    RouterModule,
  ],
  templateUrl: './hotel-detail.page.html',
  styleUrl: './hotel-detail.page.css',
})
export class HotelDetailPage implements OnInit {
  hotel?: HotelDetailModel;
  isLoading = true;
  totalRooms = 0;

  private hotelsService = inject(HotelsService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private notification = inject(NzNotificationService);

  ngOnInit() {
    // Subscribe to route parameter changes
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.loadHotelDetails(Number(id));
      } else {
        this.router.navigate(['/hotels']);
      }
    });
  }

  private async loadHotelDetails(hotelId: number) {
    this.isLoading = true;
    try {
      this.hotel = await this.hotelsService.getHotelById(hotelId);
      this.calculateTotalRooms(); //добавил чтобы считать комнаты
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load hotel details');
      await this.router.navigate(['/hotels']);
    } finally {
      this.isLoading = false;
    }
  }

  private calculateTotalRooms() {  // само функция
    if (this.hotel && this.hotel.roomTypes && this.hotel.roomTypes.length > 0) {
      this.totalRooms = this.hotel.roomTypes.reduce((total, roomType) => total + roomType.count, 0);
    } else {
      this.totalRooms = 0;
    }
  }

  goBack(): void {
    this.router.navigate(['/hotels']);
  }
}
