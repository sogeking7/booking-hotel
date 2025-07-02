import {Component, inject, OnInit} from '@angular/core';
import {CityModel, CityService, CountryModel, CountryService} from '../../../../lib/booking-hotel-api';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzSpinModule} from 'ng-zorro-antd/spin';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {NzTypographyComponent} from 'ng-zorro-antd/typography';
import {firstValueFrom} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-save-city-page',
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
    NzTypographyComponent,
  ],
  templateUrl: './city-edit.page.html',
  styleUrl: './city-edit.page.css',
})
export class CityEditPage implements OnInit {
  city?: CityModel;
  countries?: CountryModel[];
  editForm = new FormGroup({
    name: new FormControl<string | null>(null, [Validators.required]),
    countryId: new FormControl<number | null>(null, [Validators.required]),
  });
  isLoading = {
    city: false,
    save: false,
    countries: false,
  };
  isEdit = false;

  private countriesService = inject(CountryService);
  private citiesService = inject(CityService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private notification = inject(NzNotificationService);

  ngOnInit() {
    this.loadCountries();
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.loadCity(Number(id));
    }
  }

  private async loadCity(cityId: number) {
    this.isLoading.city = true;
    try {
      this.city = await firstValueFrom(this.citiesService.getCityById(cityId));
      this.editForm.patchValue({
        name: this.city.name,
        countryId: this.city.countryId,
      });
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load city');
      await this.router.navigate(['/cities']);
    } finally {
      this.isLoading.city = false;
    }
  }

  private async loadCountries() {
    this.isLoading.countries = true;
    try {
      this.countries = await firstValueFrom(this.countriesService.getAllCountries());
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load countries');
      await this.router.navigate(['/cities']);
    } finally {
      this.isLoading.countries = false;
    }
  }

  async saveCity() {
    Object.values(this.editForm.controls).forEach(control => {
      control.markAsDirty();
      control.updateValueAndValidity();
    });
    if (!this.editForm.valid) {
      return;
    }

    const formModel = this.editForm.value;
    if (!formModel.name) {
      throw new Error('required.fields');
    }

    this.isLoading.save = true;
    try {
      await firstValueFrom(
        this.citiesService.saveCity({
          id: this.city?.id,
          name: formModel.name,
          countryId: formModel.countryId ?? undefined,
        })
      );
      this.notification.success('Success', 'City has been saved successfully!');
      this.router.navigate(['/cities']);
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to save city');
    } finally {
      this.isLoading.save = false;
    }
  }

  cancel(): void {
    this.router.navigate(['/cities']);
  }
}
