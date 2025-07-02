import {Component, inject, OnInit} from '@angular/core';
import {firstValueFrom} from 'rxjs';
import {BedTypeModel, BedTypeService} from '../../../lib/booking-hotel-api';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {HttpErrorResponse} from '@angular/common/http';
import {NgForOf} from '@angular/common';
import {NzButtonComponent} from 'ng-zorro-antd/button';
import {NzIconDirective} from 'ng-zorro-antd/icon';
import {NzPopconfirmDirective} from 'ng-zorro-antd/popconfirm';
import {NzTableModule} from 'ng-zorro-antd/table';
import {RouterLink} from '@angular/router';
import {NzTypographyComponent} from 'ng-zorro-antd/typography';

@Component({
  selector: 'app-beds-page',
  standalone: true,
  imports: [NgForOf, NzButtonComponent, NzIconDirective, NzPopconfirmDirective, NzTableModule, RouterLink, NzTypographyComponent],
  templateUrl: './beds.page.html',
  styleUrl: './beds.page.css',
})
export class BedsPage implements OnInit {
  bedTypes: BedTypeModel[] = [];
  isLoading = {
    bedTypes: false,
    delete: false,
  };
  page = 1;
  size = 10;

  private bedTypeService = inject(BedTypeService);
  private notification = inject(NzNotificationService);

  async ngOnInit() {
    await this.loadBedTypes();
  }

  private async loadBedTypes() { // Load all bed types
    this.isLoading.bedTypes = true;
    try {
      this.bedTypes = await firstValueFrom(this.bedTypeService.getAllBedTypes());
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load bed types');
    } finally {
      this.isLoading.bedTypes = false;
    }
  }

  async deleteBedType(bedTypeId: number) {
    this.isLoading.delete = true;
    try {
      await firstValueFrom(this.bedTypeService.deleteBedTypeById(bedTypeId));
      this.notification.success('Success', 'Bed type has been deleted successfully!');
      await this.loadBedTypes();
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to delete bed type. Please try again.');
    } finally {
      this.isLoading.delete = false;
    }
  }
}
