import { Component, inject, OnInit } from '@angular/core';
import { CityModel, CityService } from '../../../../lib/booking-hotel-api';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { firstValueFrom } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';
import { Hotel, HotelsService } from '../../hotels/HotelsService';

@Component({
  selector: 'app-save-hotel-page',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzSelectModule,
    NzSpinModule,
    NzIconModule,
    RouterModule,
    NzTypographyModule,
  ],
  templateUrl: './hotel-edit.page.html',
  styleUrl: './hotel-edit.page.css',
})
export class HotelEditPage implements OnInit {
  hotel?: Hotel;
  cities?: CityModel[];
  editForm = new FormGroup({
    name: new FormControl<string | null>(null, [Validators.required]),
    address: new FormControl<string | null>(null, [Validators.required]),
    phone: new FormControl<string | null>(null, [Validators.required]),
    cityId: new FormControl<number | null>(null, [Validators.required]),
  });
  isLoading = {
    hotel: false,
    save: false,
    cities: false,
  };
  isEdit = false;

  private citiesService = inject(CityService);
  private hotelsService = inject(HotelsService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private notification = inject(NzNotificationService);

  ngOnInit() {
    this.loadCities();

    // Subscribe to route parameter changes
    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.isEdit = true;
        this.loadHotel(Number(id));
      } else {
        this.isEdit = false;
        // Reset form for new hotel
        this.editForm.reset();
      }
    });
  }

  private async loadHotel(hotelId: number) {
    this.isLoading.hotel = true;
    try {
      this.hotel = await firstValueFrom(this.hotelsService.getHotelById(hotelId));
      this.editForm.patchValue({
        name: this.hotel.name,
        address: this.hotel.address,
        phone: this.hotel.phone,
        cityId: this.hotel.cityId || null,
      });
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load hotel');
      await this.router.navigate(['/hotels']);
    } finally {
      this.isLoading.hotel = false;
    }
  }

  private async loadCities() {
    this.isLoading.cities = true;
    try {
      this.cities = await firstValueFrom(this.citiesService.getAllCities());
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load cities');
      await this.router.navigate(['/hotels']);
    } finally {
      this.isLoading.cities = false;
    }
  }

  async saveHotel() {
    Object.values(this.editForm.controls).forEach(control => {
      control.markAsDirty();
      control.updateValueAndValidity();
    });
    if (!this.editForm.valid) {
      return;
    }

    const formModel = this.editForm.value;
    if (!formModel.name || !formModel.address || !formModel.phone) {
      throw new Error('required.fields');
    }

    this.isLoading.save = true;
    try {
      await firstValueFrom(
        this.hotelsService.saveHotel({
          id: this.hotel?.id,
          name: formModel.name,
          address: formModel.address,
          phone: formModel.phone,
          cityId: formModel.cityId ?? undefined,
        })
      );
      this.notification.success('Success', 'Hotel has been saved successfully!');
      this.router.navigate(['/hotels']);
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to save hotel');
    } finally {
      this.isLoading.save = false;
    }
  }

  cancel(): void {
    this.router.navigate(['/hotels']);
  }
}
