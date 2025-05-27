import { Component, OnInit } from '@angular/core';
import { HotelsService, Hotel } from './HotelsService';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { CommonModule } from '@angular/common';


@Component({
  selector: 'app-hotels',
  standalone: true,
  templateUrl: './hotels.component.html',
  styleUrls: ['./hotels.component.css'],
  imports: [
    NzTableModule,
    CommonModule,
    NzButtonModule, // Example: If you're using NzButton
  ],})





export class HotelsComponent implements OnInit {
  hotels: Hotel[] = [];
  loading = true;
  pageIndex = 1;
  pageSize = 10;
  total = 0;

  constructor(private hotelsService: HotelsService) {}

  ngOnInit(): void {
    this.loadHotels();
  }

  // Fetch the hotels from the backend service
  loadHotels(): void {
    this.loading = true;

    // Call the service to get data from the backend
    this.hotelsService.getAllHotels().subscribe(
      (data: Hotel[]) => {
        this.hotels = data;
        this.total = data.length; // Set the total number of entries
        this.loading = false;
      },
      (error) => {
        console.error('Error fetching hotels:', error);
        this.loading = false;
      }
    );
  }

  // Handle query parameter changes (pagination updates)
  onQueryParamsChange(params: any): void {
    const { pageSize, pageIndex } = params;
    this.pageSize = pageSize;
    this.pageIndex = pageIndex;
    // Reload or filter data based on pagination
  }
}
