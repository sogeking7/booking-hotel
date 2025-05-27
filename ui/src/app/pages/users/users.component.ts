import { Component, OnInit } from '@angular/core';
import { UserModel, UserService } from '../../../lib/booking-hotel-api';
import { NzTableModule, NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NgForOf } from '@angular/common';
import { NzButtonComponent } from 'ng-zorro-antd/button';
import { getRoleTagColor } from '../../../lib/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { NzIconDirective } from 'ng-zorro-antd/icon';
import { NzTypographyComponent } from 'ng-zorro-antd/typography';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { NzPopconfirmDirective } from 'ng-zorro-antd/popconfirm';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  imports: [NzTableModule, NgForOf, NzButtonComponent, NzTagModule, RouterLink, NzIconDirective, NzTypographyComponent, NzPopconfirmDirective],
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  users: UserModel[] = [];

  loading = true;
  page = 1;
  size = 10;
  total = 0;

  constructor(
    private usersService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private notification: NzNotificationService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const pageIndexFromUrl = params['page'];
      const pageSizeFromUrl = params['size'];

      this.page = pageIndexFromUrl ? +pageIndexFromUrl : 1;
      this.size = pageSizeFromUrl ? +pageSizeFromUrl : 10;

      this.loadUsers();
    });
  }

  private loadUsers(): void {
    this.loading = true;
    this.usersService.getAllUsers(this.page, this.size).subscribe(list => {
      this.loading = false;
      this.total = list.totalElements;
      this.users = list.content;
    });
  }

  onQueryParamsChange(params: NzTableQueryParams): void {
    const { pageSize, pageIndex } = params;

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        page: pageIndex,
        size: pageSize,
      },
      queryParamsHandling: 'merge',
    });
  }

  public getRoleTagColor = getRoleTagColor;

  deleteUser(userId: number): void {
    this.usersService.deleteUserById(userId).subscribe({
      next: () => {
        this.loadUsers();
        this.notification.success('Success', 'User has been deleted successfully!');
      },
      error: err => {
        console.error('Failed to delete user:', err);
        this.notification.error('Error', err.error?.description || 'Failed to delete user. Please try again.');
      },
    });
  }
}
