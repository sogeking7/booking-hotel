import { Routes } from '@angular/router';
import { UsersComponent } from './users.component';
import { SaveUserComponent } from './save-user/save-user.component';

export const USERS_ROUTES: Routes = [
  { path: '', component: UsersComponent },
  { path: 'save', component: SaveUserComponent },
];
