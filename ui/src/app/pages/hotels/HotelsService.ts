import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define the hotel interface to match the backend data structure
export interface Hotel {
  id: number;
  name: string;
  address: string;
  phone: string;
  cityId?: number;
}

// HotelsService to handle HTTP requests
@Injectable({
  providedIn: 'root',
})
export class HotelsService {
  private apiUrl = '/core/hotels'; // Backend API endpoint

  constructor(private http: HttpClient) {}

  // Fetch all hotels from the backend
  getAllHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(this.apiUrl);
  }
}
