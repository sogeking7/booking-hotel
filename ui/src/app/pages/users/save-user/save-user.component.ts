import { Component, OnInit } from '@angular/core';
import { UserModel, UserSaveRequest, UserService } from '../../../../lib/booking-hotel-api';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzTypographyComponent } from 'ng-zorro-antd/typography';

@Component({
  selector: 'app-save-user',
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzSelectModule,
    NzSpinModule,
    NzIconModule,
    RouterModule,
    NzTypographyComponent,
  ],
  templateUrl: './save-user.component.html',
  styleUrl: './save-user.component.css',
})
export class SaveUserComponent implements OnInit {
  userId: number | null = null;
  user: UserModel | null = null;
  editForm: FormGroup | null = null;
  isLoading = false;
  isSubmitting = false;

  passwordVisible = false;

  constructor(
    private usersService: UserService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private notification: NzNotificationService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      const idParam = params['id'];
      this.userId = idParam ? +idParam : null;
      this.loadUser();
    });
  }

  private loadUser(): void {
    if (!this.userId) {
      this.initForm(null);
      return;
    }
    this.usersService.getUserById(this.userId).subscribe({
      next: user => {
        this.user = user;
        this.initForm(user);
        this.isLoading = false;
      },
      error: error => {
        this.isLoading = false;
        this.initForm(null);
        this.notification.error('Error', error.error?.detail || 'Failed to load user data');
        this.router.navigate(['/users']);
      },
    });
  }

  private initForm(user: UserModel | null): void {
    if (user) {
      this.editForm = this.fb.group({
        id: [user.id, [Validators.required]],
        firstName: [user.firstName, [Validators.required]],
        lastName: [user.lastName, [Validators.required]],
        email: [user.email, [Validators.required, Validators.email]],
        role: [user.role, [Validators.required]],
      });
    } else {
      this.editForm = this.fb.group({
        firstName: ['', [Validators.required]],
        lastName: ['', [Validators.required]],
        email: ['', [Validators.required, Validators.email]],
        role: [null, [Validators.required]],
        password: [null, [Validators.required, Validators.minLength(8)]],
      });
    }
  }

  saveUser(): void {
    if (!this.editForm) {
      return;
    }

    if (this.editForm.valid) {
      this.isSubmitting = true;

      const userData: UserSaveRequest = { ...this.editForm.value };

      this.usersService.saveUser(userData).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.notification.success('Success', 'User has been saved successfully!');
          this.router.navigate(['/users']);
        },
        error: err => {
          this.isSubmitting = false;
          this.notification.error('Error', err.error?.detail || 'Failed to save user. Please try again.');
        },
      });
    } else {
      Object.values(this.editForm.controls).forEach(control => {
        if (control.invalid) {
          control.markAsDirty();
          control.updateValueAndValidity({ onlySelf: true });
        }
      });
    }
  }

  cancel(): void {
    this.router.navigate(['/users']);
  }

  togglePasswordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }
}
