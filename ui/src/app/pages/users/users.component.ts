import { Component, OnInit } from '@angular/core';
import { UserModel, UserService } from '../../../lib/booking-hotel-api';
import { NzTableModule, NzTableQueryParams } from 'ng-zorro-antd/table';
import { NzTagModule } from 'ng-zorro-antd/tag';
import { NgForOf } from '@angular/common';
import { NzButtonComponent } from 'ng-zorro-antd/button';
import { getRoleTagColor } from '../../../lib/common';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  imports: [NzTableModule, NgForOf, NzButtonComponent, NzTagModule],
  styleUrls: ['./users.component.css'],
})
export class UsersComponent implements OnInit {
  users: UserModel[] = [];

  loading = true;
  pageIndex = 1;
  pageSize = 10;
  total = 0;

  constructor(
    private usersService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const pageIndexFromUrl = params['pageIndex'];
      const pageSizeFromUrl = params['pageSize'];

      this.pageIndex = pageIndexFromUrl ? +pageIndexFromUrl : 1;
      this.pageSize = pageSizeFromUrl ? +pageSizeFromUrl : 10;

      this.loadUsers();
    });
  }

  searchData(reset: boolean = false): void {
    let targetPageIndex = this.pageIndex;
    if (reset) {
      targetPageIndex = 1;
    }

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        pageIndex: targetPageIndex,
        pageSize: this.pageSize,
      },
      queryParamsHandling: 'merge',
    });
  }

  private loadUsers(): void {
    this.loading = true;
    this.usersService.getAllUsers(this.pageIndex - 1, this.pageSize).subscribe(list => {
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
        pageIndex: pageIndex,
        pageSize: pageSize,
      },
      queryParamsHandling: 'merge',
    });
  }

  public getRoleTagColor = getRoleTagColor;
}
