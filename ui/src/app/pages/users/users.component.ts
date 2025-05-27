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

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  imports: [NzTableModule, NgForOf, NzButtonComponent, NzTagModule, RouterLink, NzIconDirective, NzTypographyComponent],
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
    private router: Router
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

  searchData(reset: boolean = false): void {
    let targetPageIndex = this.page;
    if (reset) {
      targetPageIndex = 1;
    }

    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        pageIndex: targetPageIndex,
        pageSize: this.size,
      },
      queryParamsHandling: 'merge',
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
}
