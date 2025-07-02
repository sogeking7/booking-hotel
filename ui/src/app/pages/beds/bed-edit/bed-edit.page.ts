import {Component, inject, OnInit} from '@angular/core';
import {BedTypeModel, BedTypeService} from '../../../../lib/booking-hotel-api';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzSpinModule} from 'ng-zorro-antd/spin';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {NzTypographyComponent} from 'ng-zorro-antd/typography';
import {firstValueFrom} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';

@Component({
  selector: 'app-save-bed-page',
  standalone: true,
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
  templateUrl: './bed-edit.page.html',
  styleUrl: './bed-edit.page.css',
})
export class BedEditPage implements OnInit {
  bedType?: BedTypeModel;
  editForm = new FormGroup({
    name: new FormControl<string | null>(null, [Validators.required]),
    iconRef: new FormControl<string | null>(null, [Validators.required]),
  });
  isLoading = {
    bedType: false,
    save: false,
  };
  isEdit = false;

  private bedTypeService = inject(BedTypeService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private notification = inject(NzNotificationService);

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.loadBedType(Number(id));
    }
  }

  private async loadBedType(bedTypeId: number) {
    this.isLoading.bedType = true;
    try {
      this.bedType = await firstValueFrom(this.bedTypeService.getBedTypeById(bedTypeId));
      this.editForm.patchValue({
        name: this.bedType.name,
        iconRef: this.bedType.iconRef,
      });
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load bed type');
      await this.router.navigate(['/beds']);
    } finally {
      this.isLoading.bedType = false;
    }
  }

  async saveBedType() {
    Object.values(this.editForm.controls).forEach(control => {
      control.markAsDirty();
      control.updateValueAndValidity();
    });
    if (!this.editForm.valid) {
      return;
    }

    const formModel = this.editForm.value;
    if (!formModel.name || !formModel.iconRef) {
      throw new Error('required.fields');
    }

    this.isLoading.save = true;
    try {
      await firstValueFrom(
        this.bedTypeService.saveBedType({
          id: this.bedType?.id,
          name: formModel.name,
          iconRef: formModel.iconRef,
        })
      );
      this.notification.success('Success', 'Bed type has been saved successfully!');
      this.router.navigate(['/beds']);
    } catch (e: unknown) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to save bed type');
    } finally {
      this.isLoading.save = false;
    }
  }

  cancel(): void {
    this.router.navigate(['/beds']);
  }
}
