import {Component, inject, NgModule, OnInit} from '@angular/core';
import { HotelsService } from './HotelsService';
import { HotelModel } from '@lib/booking-hotel-api';


import { NzTableModule } from 'ng-zorro-antd/table';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { CommonModule } from '@angular/common';
import {NzIconDirective} from "ng-zorro-antd/icon";
import {NzTypographyComponent} from "ng-zorro-antd/typography";
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {FormsModule } from '@angular/forms'; // Import FormsModule for ngModel
import {debounceTime, distinctUntilChanged, Subject} from 'rxjs';
import {takeUntilDestroyed} from '@angular/core/rxjs-interop';


@Component({
  selector: 'app-hotels',
  standalone: true,
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.css'],
  imports: [
    NzTableModule,
    CommonModule,
    NzButtonModule,
    NzIconDirective,
    NzTypographyComponent,
    RouterLink,
    FormsModule,
    // Example: If you're using NzButton
  ],
})
export class HotelsComponent implements OnInit {
  hotels: HotelModel[] = [];
  loading = true;
  pageIndex = 1;
  pageSize = 10;
  total = 0;
  searchTerm: string = '';

  private onSearchSubject = new Subject<string>();
  private hotelsService = inject(HotelsService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  constructor() {
    this.onSearchSubject.pipe(
      takeUntilDestroyed(),
      debounceTime(500),
      distinctUntilChanged()
    ).subscribe(search => {
      this.loadHotels(search);
    });
  }

  ngOnInit(): void {
    // Read query parameters from URL
    this.route.queryParams.subscribe(params => {
      if (params['search']) {
        this.searchTerm = params['search'];
      }

      if (params['pageIndex']) {
        this.pageIndex = +params['pageIndex'];
      }

      if (params['pageSize']) {
        this.pageSize = +params['pageSize'];
      }

      // Load hotels with the parameters from URL
      this.loadHotels(this.searchTerm);
    });
  }

  // Fetch the hotels from the backend service
  async loadHotels(searchOverride?: string): Promise<void> {
    this.loading = true;

    const searchTerm = searchOverride !== undefined ? searchOverride : this.searchTerm;
    this.searchTerm = searchTerm;

    try {
      if (searchTerm && searchTerm.trim() !== '') {
        this.hotels = await this.hotelsService.searchHotels(searchTerm);
      } else {
        this.hotels = await this.hotelsService.getAllHotels();
      }

      this.total = this.hotels.length;
    } catch (error) {
      console.error('Error fetching hotels:', error);
    } finally {
      this.loading = false;
      this.updateUrlQueryParams();
    }
  }

  onSearchChange(): void {
    this.onSearchSubject.next(this.searchTerm);
    this.updateUrlQueryParams();
  }

  onSearch(): void {
    this.onSearchSubject.next(this.searchTerm);
    this.updateUrlQueryParams();
  }

  onQueryParamsChange(params: any): void {
    const { pageSize, pageIndex } = params;
    this.pageSize = pageSize;
    this.pageIndex = pageIndex;
    this.updateUrlQueryParams();
  }

  private updateUrlQueryParams(): void {
    const queryParams: any = {};

    if (this.searchTerm) {
      queryParams.search = this.searchTerm;
    }

    queryParams.pageIndex = this.pageIndex;
    queryParams.pageSize = this.pageSize;

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: queryParams,
      queryParamsHandling: 'merge',
      replaceUrl: true,
    });
  }
}
