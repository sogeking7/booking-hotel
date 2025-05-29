import { Routes } from '@angular/router';
import { CitiesPage } from './cities.page';
import { CityEditPage } from './city-edit/city-edit.page';

export const CITIES_ROUTES: Routes = [
  { path: '', component: CitiesPage },
  {
    path: ':id/edit',
    component: CityEditPage,
    resolve: {},
  },
  { path: 'new', component: CityEditPage },
];
