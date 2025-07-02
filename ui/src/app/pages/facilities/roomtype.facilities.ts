import {Component, inject, OnInit} from '@angular/core';
import {firstValueFrom} from 'rxjs';
import {RoomTypeFacilityModel, RoomTypeFacilityService} from '../../../lib/booking-hotel-api';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {HttpErrorResponse} from '@angular/common/http';
import {DatePipe, NgForOf} from '@angular/common';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzPopconfirmDirective} from 'ng-zorro-antd/popconfirm';
import {NzTableModule} from 'ng-zorro-antd/table';
import {RouterLink} from '@angular/router';
import {NzTypographyComponent} from 'ng-zorro-antd/typography';

@Component({
  selector: 'app-roomtype-facilities-page',
  standalone: true,
  imports: [NgForOf, DatePipe, NzButtonComponent, NzIconDirective, NzPopconfirmDirective, NzTableModule, RouterLink, NzTypographyComponent],
  templateUrl: './roomtype.facilities.html',
  styleUrl: './roomtype.facilities.css',
})
export class RoomTypeFacilitiesPage implements OnInit {
  roomTypeFacilities: RoomTypeFacilityModel[] = [];
  isLoading = {
    roomTypeFacilities: false,
    delete: false,
  };
  page = 1;
  size = 10;

  private roomTypeFacilityService = inject(RoomTypeFacilityService);
  private notification = inject(NzNotificationService);

  async ngOnInit() {
    await this.loadRoomTypeFacilities();
  }

  private async loadRoomTypeFacilities() {
    this.isLoading.roomTypeFacilities = true;
    try {
      this.roomTypeFacilities = await firstValueFrom(this.roomTypeFacilityService.getAllRoomTypeFacilities());
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load room type facilities');
    } finally {
      this.isLoading.roomTypeFacilities = false;
    }
  }

  async deleteRoomTypeFacility(roomTypeFacilityId: number) {
    this.isLoading.delete = true;
    try {
      await firstValueFrom(this.roomTypeFacilityService.deleteRoomTypeFacilityById(roomTypeFacilityId));
      this.notification.success('Success', 'Room type facility has been deleted successfully!');
      await this.loadRoomTypeFacilities();
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to delete room type facility. Please try again.');
    } finally {
      this.isLoading.delete = false;
    }
  }
}
