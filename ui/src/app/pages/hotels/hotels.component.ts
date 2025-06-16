import {Component, inject, NgModule, OnInit} from '@angular/core';
import { HotelsService, Hotel } from './HotelsService';
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
  hotels: Hotel[] = [];
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
  loadHotels(searchOverride?: string): void {
    this.loading = true;

    // Use provided search term or fall back to component's searchTerm
    const searchTerm = searchOverride !== undefined ? searchOverride : this.searchTerm;

    // Update the component's searchTerm
    this.searchTerm = searchTerm;

    // If search term exists, use search endpoint, otherwise get all hotels
    if (searchTerm && searchTerm.trim() !== '') {
      this.hotelsService.getHotels(searchTerm).subscribe(
        (data: Hotel[]) => {
          this.hotels = data;
          this.total = data.length; // Set the total number of entries
          this.loading = false;
          // Update URL with current state
          this.updateUrlQueryParams();
        },
        error => {
          console.error('Error fetching hotels:', error);
          this.loading = false;
        }
      );
    } else {
      // Call the service to get data from the backend
      this.hotelsService.getAllHotels().subscribe(
        (data: Hotel[]) => {
          this.hotels = data;
          this.total = data.length; // Set the total number of entries
          this.loading = false;
          // Update URL with current state
          this.updateUrlQueryParams();
        },
        error => {
          console.error('Error fetching hotels:', error);
          this.loading = false;
        }
      );
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

  // Handle query parameter changes (pagination updates)
  onQueryParamsChange(params: any): void {
    const { pageSize, pageIndex } = params;
    this.pageSize = pageSize;
    this.pageIndex = pageIndex;
    this.updateUrlQueryParams();
  }

  // Helper method to update URL query parameters
  private updateUrlQueryParams(): void {
    const queryParams: any = {};

    if (this.searchTerm) {
      queryParams.search = this.searchTerm;
    }

    queryParams.pageIndex = this.pageIndex;
    queryParams.pageSize = this.pageSize;

    // Update URL without reloading the page
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: queryParams,
      queryParamsHandling: 'merge', // Keep existing query params
      replaceUrl: true // Replace the current URL to avoid adding to browser history
    });
  }
}
