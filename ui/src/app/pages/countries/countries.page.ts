import {Component, inject, OnInit} from '@angular/core';
import {firstValueFrom} from 'rxjs';
import {CountryModel, CountryService} from '../../../lib/booking-hotel-api';
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
  selector: 'app-countries-page',
  imports: [NgForOf, NzButtonComponent, NzIconDirective, NzPopconfirmDirective, NzTableModule, RouterLink, NzTypographyComponent],
  templateUrl: './countries.page.html',
  styleUrl: './countries.page.css',
})
export class CountriesPage implements OnInit {
  countries: CountryModel[] = [];
  isLoading = {
    countries: false,
    delete: false,
  };
  page = 1;
  size = 10;

  private countryService = inject(CountryService);
  private notification = inject(NzNotificationService);

  async ngOnInit() {
    await this.loadCountries();
  }

  private async loadCountries() {
    this.isLoading.countries = true;
    try {
      this.countries = await firstValueFrom(this.countryService.getAllCountries());
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load countries');
    } finally {
      this.isLoading.countries = false;
    }
  }

  async deleteCountry(countryId: number) {
    this.isLoading.delete = true;
    try {
      await firstValueFrom(this.countryService.deleteCountryById(countryId));
      this.notification.success('Success', 'Country has been deleted successfully!');
      await this.loadCountries();
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to delete country. Please try again.');
    } finally {
      this.isLoading.delete = false;
    }
  }
}
