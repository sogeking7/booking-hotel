import { Routes } from '@angular/router';
import { UsersComponent } from './users.component';
import { UserEditComponent } from './save-user/user-edit.component';

export const USERS_ROUTES: Routes = [
  { path: '', component: UsersComponent },
  { path: ':id/edit', component: UserEditComponent },
  { path: 'new', component: UserEditComponent },
];
