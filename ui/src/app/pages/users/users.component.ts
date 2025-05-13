import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators';
import {UserService} from '../../../lib/booking-hotel-api';
import {NzTableComponent} from 'ng-zorro-antd/table';

// If you prefer a standalone component, add `standalone: true` and
// imports: [ CommonModule, NzTableModule, NzButtonModule ] here.
@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  imports: [
    NzTableComponent
  ],
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: any[] = [];
  isLoading = false;

  constructor(private usersService: UserService) {}

  ngOnInit(): void {
    this.loadUsers();
  }

  private loadUsers(): void {
    this.isLoading = true;
    this.usersService
      .apiCoreUsersGet()
      .pipe(finalize(() => (this.isLoading = false)))
      .subscribe((list) => (this.users = list));
  }
}
