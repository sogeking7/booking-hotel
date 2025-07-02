import {Routes} from '@angular/router';
import {AppLayoutComponent} from './components/layout/app-layout.component';
import {AuthGuard} from './guards/auth.guard';
import {LoginComponent} from './pages/auth/login/login.component';

export const routes: Routes = [
  {
    path: '',
    component: AppLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        redirectTo: 'welcome',
        pathMatch: 'full',
      },
      {
        path: 'welcome',
        loadChildren: () => import('./pages/welcome/welcome.routes').then(m => m.WELCOME_ROUTES),
      },
      {
        path: 'users',
        loadChildren: () => import('./pages/users/users.routes').then(m => m.USERS_ROUTES),
      },
      {
        path: 'hotels',
        loadChildren: () => import('./pages/hotels/hotels.routes').then(m => m.HOTELS_ROUTES),
      },
      {
        path: 'cities',
        loadChildren: () => import('./pages/cities/cities.routes').then(m => m.CITIES_ROUTES),
      },
      {
        path: 'beds',
        loadChildren: () => import('./pages/beds/beds.routes').then(m => m.BEDS_ROUTES),
      },
      {
        path: 'facilities',
        loadChildren: () => import('./pages/facilities/facilities.routes').then(m => m.FACILITIES_ROUTES),
      },
      {
        path: 'countries',
        loadChildren: () => import('./pages/countries/countries.routes').then(m => m.COUNTRIES_ROUTES),
      },
      {
        path: 'orders',
        loadComponent: () => import('./pages/orders/orders.page').then(m => m.OrdersPageComponent),
      }
    ],
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: '**',
    redirectTo: '',
  },
];
