import { Component, inject, OnInit } from '@angular/core';
import { UserModel, UserRole, UserService } from '../../../../lib/booking-hotel-api';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzSelectModule } from 'ng-zorro-antd/select';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzTypographyComponent } from 'ng-zorro-antd/typography';
import { firstValueFrom } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

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
  templateUrl: './user-edit.component.html',
  styleUrl: './user-edit.component.css',
})
export class UserEditComponent implements OnInit {
  user?: UserModel;
  editForm = new FormGroup({
    firstName: new FormControl<string | null>(null, [Validators.required]),
    lastName: new FormControl<string | null>(null, [Validators.required]),
    email: new FormControl<string | null>(null, [Validators.email]),
    role: new FormControl<UserRole | null>(null, [Validators.required]),
    password: new FormControl<string | null>(null, [Validators.minLength(8)]),
  });

  isLoading = {
    user: false,
    save: false,
  };
  isEdit = false;
  passwordVisible = false;
  private usersService = inject(UserService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private notification = inject(NzNotificationService);

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.loadUser(Number(id));
    }
  }

  private async loadUser(userId: number) {
    this.isLoading.user = true;
    try {
      this.user = await firstValueFrom(this.usersService.getUserById(userId));
      this.editForm.patchValue({
        firstName: this.user.firstName,
        lastName: this.user.lastName,
        role: this.user.role,
      });
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load user');
      await this.router.navigate(['/users']);
    } finally {
      this.isLoading.user = false;
    }
  }

  async saveUser() {
    Object.values(this.editForm.controls).forEach(control => {
      control.markAsDirty();
      control.updateValueAndValidity();
    });
    if (!this.editForm.valid) {
      return;
    }

    const formModel = this.editForm.value;
    if (!formModel.firstName || !formModel.lastName || !formModel.role) {
      throw new Error('required.fields');
    }

    this.isLoading.save = true;
    try {
      await firstValueFrom(
        this.usersService.saveUser({
          id: this.user?.id,
          firstName: formModel.firstName,
          lastName: formModel.lastName,
          email: formModel.email ?? undefined,
          password: formModel.password ?? undefined,
          role: formModel.role,
        })
      );
      this.notification.success('Success', 'User has been saved successfully!');
      this.router.navigate(['/users']);
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to save user');
    } finally {
      this.isLoading.save = false;
    }
  }

  cancel(): void {
    this.router.navigate(['/users']);
  }

  togglePasswordVisibility(): void {
    this.passwordVisible = !this.passwordVisible;
  }
}
