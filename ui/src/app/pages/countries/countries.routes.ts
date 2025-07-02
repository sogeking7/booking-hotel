import {Routes} from '@angular/router';
import {CountriesPage} from './countries.page';
import {CountriesEditPage} from './country-edit/countries-edit.page';

export const COUNTRIES_ROUTES: Routes = [
  { path: '', component: CountriesPage },
  {
    path: ':id/edit',
    component: CountriesEditPage,
    resolve: {},
  },
  { path: 'new', component: CountriesEditPage },
];
