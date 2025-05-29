import { Routes } from '@angular/router';
import { UsersPage } from './users.page';
import { UserEditPage } from './save-user/user-edit.page';

export const USERS_ROUTES: Routes = [
  { path: '', component: UsersPage },
  { path: ':id/edit', component: UserEditPage },
  { path: 'new', component: UserEditPage },
];
