import { Component, inject, OnDestroy, OnInit } from '@angular/core';
import { NgForOf } from '@angular/common';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { firstValueFrom, Subscription } from 'rxjs';

import { UserModel, UserService } from '../../../lib/booking-hotel-api';
import { getRoleTagColor } from '../../../lib/common';

import { NzTableModule, NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NzButtonComponent } from 'ng-zorro-antd/button';
import { NzNotificationService } from 'ng-zorro-antd/notification';

@Component({
  selector: 'app-users-page',
  templateUrl: './users.page.html',
  styleUrls: ['./users.page.css'],
  imports: [
    NzTableModule,
    NgForOf,
    NzButtonComponent,
    NzTagModule,
    RouterLink,
  ],
  standalone: true,
})
export class UsersPage implements OnInit, OnDestroy {
  private queryParamsSub!: Subscription;

  users: UserModel[] = [];
  page = 1;
  size = 10;
  total = 0;

  isLoading = {
    users: false,
    delete: false,
  };

  private usersService = inject(UserService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private notification = inject(NzNotificationService);

  async ngOnInit() {
    this.queryParamsSub = this.route.queryParams.subscribe(params => {
      this.page = params['page'] ? Number(params['page']) : 1;
      this.size = params['size'] ? Number(params['size']) : 10;
    });
    await this.loadUsers();
  }

  ngOnDestroy(): void {
    this.queryParamsSub?.unsubscribe();
  }

  private async loadUsers() {
    this.isLoading.users = true;
    try {
      const res = await firstValueFrom(this.usersService.getAllUsers(this.page, this.size));
      this.total = res.totalElements;
      this.users = res.content;
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load users');
    } finally {
      this.isLoading.users = false;
    }
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

  async deleteUser(userId: number) {
    this.isLoading.delete = true;
    try {
      await firstValueFrom(this.usersService.deleteUserById(userId));
      this.notification.success('Success', 'User has been deleted successfully!');
      await this.loadUsers();
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to delete user. Please try again.');
    } finally {
      this.isLoading.delete = false;
    }
  }
}
