import { Component, inject, OnInit } from '@angular/core';
import { firstValueFrom } from 'rxjs';
import { CityModel, CityService } from '../../../lib/booking-hotel-api';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { HttpErrorResponse } from '@angular/common/http';
import { NgForOf } from '@angular/common';
import { NzButtonComponent } from 'ng-zorro-antd/button';
import { NzIconDirective } from 'ng-zorro-antd/icon';
import { NzPopconfirmDirective } from 'ng-zorro-antd/popconfirm';
import { NzTableModule } from 'ng-zorro-antd/table';
import { RouterLink } from '@angular/router';
import { NzTypographyComponent } from 'ng-zorro-antd/typography';

@Component({
  selector: 'app-cities-page',
  imports: [NgForOf, NzButtonComponent, NzIconDirective, NzPopconfirmDirective, NzTableModule, RouterLink, NzTypographyComponent],
  templateUrl: './cities.page.html',
  styleUrl: './cities.page.css',
})
export class CitiesPage implements OnInit {
  cities: CityModel[] = [];
  isLoading = {
    cities: false,
    delete: false,
  };
  page = 1;
  size = 10;

  private citiesService = inject(CityService);
  private notification = inject(NzNotificationService);

  async ngOnInit() {
    await this.loadCities();
  }

  private async loadCities() {
    this.isLoading.cities = true;
    try {
      this.cities = await firstValueFrom(this.citiesService.getAllCities());
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load cities');
    } finally {
      this.isLoading.cities = false;
    }
  }

  async deleteCity(cityId: number) {
    this.isLoading.delete = true;
    try {
      await firstValueFrom(this.citiesService.deleteCityById(cityId));
      this.notification.success('Success', 'City has been deleted successfully!');
      await this.loadCities();
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to delete city. Please try again.');
    } finally {
      this.isLoading.delete = false;
    }
  }
}
