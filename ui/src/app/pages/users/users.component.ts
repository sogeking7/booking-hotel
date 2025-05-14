import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators';
import {UserModel, UserService} from '../../../lib/booking-hotel-api';
import { NzTableModule, NzThMeasureDirective} from 'ng-zorro-antd/table';
import {NgForOf} from '@angular/common';
import {NzButtonComponent} from 'ng-zorro-antd/button';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  imports: [
    NzTableModule,
    NgForOf,
    NzButtonComponent,
    NzThMeasureDirective,
  ],
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: UserModel[] = [];
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
