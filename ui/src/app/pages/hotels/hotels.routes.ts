import { Routes } from '@angular/router';
import { HotelsComponent } from './hotels.component';
import { HotelEditPage } from './hotel-edit/hotel-edit.page';
import { HotelDetailPage } from './hotel-detail/hotel-detail.page';

export const HOTELS_ROUTES: Routes = [
  { path: '', component: HotelsComponent },
  {
    path: ':id/edit',
    component: HotelEditPage,
    resolve: {},
  },
  {
    path: ':id',
    component: HotelDetailPage,
  },
  { path: 'new', component: HotelEditPage },
];
