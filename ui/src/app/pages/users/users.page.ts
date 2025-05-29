import { Component, inject, OnDestroy, OnInit } from '@angular/core';
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
import { firstValueFrom, Subscription } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-users-page',
  templateUrl: './users.page.html',
  imports: [NzTableModule, NgForOf, NzButtonComponent, NzTagModule, RouterLink, NzIconDirective, NzTypographyComponent, NzPopconfirmDirective],
  styleUrls: ['./users.page.css'],
})
export class UsersPage implements OnInit, OnDestroy {
  private queryParamsSub!: Subscription;

  users: UserModel[] = [];

  isLoading = {
    users: false,
    delete: false,
  };
  page = 1;
  size = 10;
  total = 0;

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
    if (this.queryParamsSub) {
      this.queryParamsSub.unsubscribe();
    }
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
