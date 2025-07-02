import {Routes} from '@angular/router';
import {BedsPage} from './beds.page';
import {BedEditPage} from './bed-edit/bed-edit.page';

export const BEDS_ROUTES: Routes = [
  { path: '', component: BedsPage },
  {
    path: ':id/edit',
    component: BedEditPage,
    resolve: {},
  },
  { path: 'new', component: BedEditPage },
];
