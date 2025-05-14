import { Component, OnInit } from '@angular/core';
import { finalize } from 'rxjs/operators';
import {UserModel, UserService} from '../../../lib/booking-hotel-api';
import { NzTableModule, NzThMeasureDirective} from 'ng-zorro-antd/table';
import {NgForOf, NgSwitch, NgSwitchCase, NgSwitchDefault} from '@angular/common';
import {NzButtonComponent} from 'ng-zorro-antd/button';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  imports: [
    NzTableModule,
    NgForOf,
    NzButtonComponent,
    NzThMeasureDirective,
    NgSwitch,
    NgSwitchCase,
    NgSwitchDefault
  ],
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {
  users: UserModel[] = [];
  isLoading = false;
  // Add this to your existing component class
  tableColumns = [
    { title: 'ID', key: 'id', width: '10%' },
    { title: 'First name', key: 'firstName', width: '30%' },
    { title: 'Last name', key: 'lastName', width: '30%' },
    { title: 'Email', key: 'email', width: '40%' },
    { title: 'Actions', key: 'actions', width: '20%' }
  ];

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
