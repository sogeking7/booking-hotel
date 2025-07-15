import {Component, inject, OnInit} from '@angular/core';
import {CountryModel, CountryService} from '../../../../lib/booking-hotel-api';
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
import { signal, computed, effect, WritableSignal } from '@angular/core';

@Component({
  selector: 'app-save-country-page',
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
  templateUrl: './countries-edit.page.html',
  styleUrl: './countries-edit.page.css',
})
export class CountriesEditPage implements OnInit {
  country?: CountryModel;
  editForm = new FormGroup({
    name: new FormControl<string | null>(null, [Validators.required]),
    code: new FormControl<string | null>(null, [Validators.required]),
    currency: new FormControl<string | null>(null, [Validators.required]),
  });
  isLoading = {
    country: false,
    save: false,
  };
  isEdit = false;

  private countryService = inject(CountryService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private notification = inject(NzNotificationService);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.loadCountry(Number(id));
    }
  }

  private async loadCountry(countryId: number) {
    this.isLoading.country = true;
    try {
      this.country = await firstValueFrom(this.countryService.getCountryById(countryId));
      this.editForm.patchValue({
        name: this.country.name,
        code: this.country.code,
        currency: this.country.currency,
      });
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load country');
      await this.router.navigate(['/countries']);
    } finally {
      this.isLoading.country = false;
    }
  }

  async saveCountry() {
    Object.values(this.editForm.controls).forEach(control => {
      control.markAsDirty();
      control.updateValueAndValidity();
    });
    if (!this.editForm.valid) {
      return;
    }

    const formModel = this.editForm.value;
    if (!formModel.name || !formModel.code || !formModel.currency) {
      throw new Error('required.fields');
    }

    this.isLoading.save = true;
    try {
      await firstValueFrom(
        this.countryService.saveCountry({
          id: this.country?.id,
          name: formModel.name,
          code: formModel.code,
          currency: formModel.currency,
        })
      );
      this.notification.success('Success', 'Country has been saved successfully!');
      this.router.navigate(['/countries']);
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to save country');
    } finally {
      this.isLoading.save = false;
    }
  }

  cancel(): void {
    this.router.navigate(['/countries']);
  }
}
