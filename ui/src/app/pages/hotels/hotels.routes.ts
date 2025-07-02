import {Routes} from '@angular/router';

export const HOTELS_ROUTES: Routes = [
  {
    path: '',
    loadComponent: () => import('./hotels.component').then(m => m.HotelsComponent),
  },
  {
    path: 'new',
    loadComponent: () => import('./hotel-edit/hotel-edit.page').then(m => m.HotelEditPage),
  },
  {
    path: ':id/edit',
    loadComponent: () => import('./hotel-edit/hotel-edit.page').then(m => m.HotelEditPage),
  },
  {
    path: ':id/rooms/new',
    loadComponent: () => import('./room-types/room-form.component').then(m => m.RoomFormComponent),
  },
  {
    path: ':id/rooms/:roomId/edit',
    loadComponent: () => import('./room-types/room-form.component').then(m => m.RoomFormComponent),
  },
  {
    path: ':id/rooms',
    loadComponent: () => import('./room-types/rooms.component').then(m => m.RoomsComponent),
  },
  {
    path: ':id',
    loadComponent: () => import('./hotel-detail/hotel-detail.page').then(m => m.HotelDetailPage),
  },
];
