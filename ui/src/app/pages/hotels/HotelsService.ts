import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

// Define the hotel interface to match the backend data structure
export interface Hotel {
  id?: number;
  name: string;
  address: string;
  phone: string;
  cityId?: number;
}

export interface BedType {
  id: number;
  name: string;
  iconRef: string;
}

export interface Facility {
  id: number;
  name: string;
  iconRef: string;
  type?: string;
}

export interface RoomType {
  id: number;
  hotelId: number;
  bedTypeId: number;
  name: string;
  count: number;
  bedType: BedType;
  facilities: Facility[];
}

export interface HotelDetail extends Hotel {
  roomTypes: RoomType[];
}

// HotelsService to handle HTTP requests
@Injectable({
  providedIn: 'root',
})
export class HotelsService {
  private apiUrl = '/api/core/hotels'; // Backend API endpoint

  constructor(private http: HttpClient) {}

  // Fetch all hotels from the backend
  getAllHotels(): Observable<Hotel[]> {
    return this.http.get<Hotel[]>(this.apiUrl);
  }

  // Get a hotel by ID
  getHotelById(id: number): Observable<Hotel> {
    return this.http.get<Hotel>(`${this.apiUrl}/${id}`);
  }

  // Get hotel details with rooms, bed types, and facilities
  getHotelDetails(id: number): Observable<HotelDetail> {
    return this.http.get<HotelDetail>(`${this.apiUrl}/${id}/details`);
  }

  // Save (create or update) a hotel
  saveHotel(hotel: Hotel): Observable<Hotel> {
    if (hotel.id) {
      // Update existing hotel
      return this.http.put<Hotel>(`${this.apiUrl}/${hotel.id}`, hotel);
    } else {
      // Create new hotel
      return this.http.post<Hotel>(this.apiUrl, hotel);
    }
  }

  getHotels(search: string) {
    return this.http.get<Hotel[]>(`${this.apiUrl}/search`, {
      params: { search }
    });
  }


  // Delete a hotel
  deleteHotel(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
