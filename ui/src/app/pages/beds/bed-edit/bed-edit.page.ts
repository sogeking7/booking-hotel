import {Component, inject, OnInit} from '@angular/core';
import {BedTypeModel, BedTypeService, FileService} from '../../../../lib/booking-hotel-api';
import {ActivatedRoute, Router, RouterModule} from '@angular/router';
import {NzNotificationService} from 'ng-zorro-antd/notification';
import {FormControl, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule, NgIf} from '@angular/common';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzSpinModule} from 'ng-zorro-antd/spin';
import {NzIconModule} from 'ng-zorro-antd/icon';
import {NzTypographyComponent} from 'ng-zorro-antd/typography';
import {firstValueFrom} from 'rxjs';
import {HttpErrorResponse} from '@angular/common/http';
import {NzUploadFile, NzUploadModule} from 'ng-zorro-antd/upload';
// ДОБАВЛЕНО: Импорты для модального окна
import {NzModalModule, NzModalService} from 'ng-zorro-antd/modal';


@Component({
  selector: 'app-save-bed-page',
  standalone: true,
  imports: [
    CommonModule,
    NgIf,
    ReactiveFormsModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzSelectModule,
    NzSpinModule,
    NzIconModule,
    RouterModule,
    NzTypographyComponent,
    NzUploadModule,
    NzModalModule, // <-- ДОБАВЛЕНО
  ],
  templateUrl: './bed-edit.page.html',
  styleUrl: './bed-edit.page.css',
})
export class BedEditPage implements OnInit {
  bedType?: BedTypeModel;
  selectedFile?: File;
  previewImage: string | undefined;

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
  private fileService = inject(FileService);
  // ДОБАВЛЕНО: Внедрение сервиса для модальных окон
  private modal = inject(NzModalService);


  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.loadBedType(Number(id));
    }
  }

  beforeUpload = (file: NzUploadFile): boolean => {
    this.selectedFile = file as unknown as File;
    this.editForm.patchValue({
      iconRef: file.name
    });

    const reader = new FileReader();
    reader.readAsDataURL(this.selectedFile);
    reader.onload = () => {
      this.previewImage = reader.result as string;
    };

    return false;
  };

  // ДОБАВЛЕНО: Метод для открытия фото в модальном окне
  handlePreview(): void {
    if (!this.previewImage) {
      return;
    }
    this.modal.create({
      nzTitle: 'Просмотр иконки',
      nzContent: `<div class="flex justify-center"><img src="${this.previewImage}" alt="Превью" style="max-width: 100%;"/></div>`,
      nzFooter: null, // Убираем кнопки OK/Отмена
      nzClosable: true, // Позволяем закрывать окно
      nzCentered: true
    });
  }

  private async loadBedType(bedTypeId: number) {
    this.isLoading.bedType = true;
    try {
      this.bedType = await firstValueFrom(this.bedTypeService.getBedTypeById(bedTypeId));
      this.editForm.patchValue({
        name: this.bedType.name,
        iconRef: this.bedType.iconRef,
      });

      if (this.bedType.iconRef) {
        this.previewImage = 'http://localhost:8080/' + this.bedType.iconRef;
      }

    } catch (e: unknown) {
      const errorMessage = e instanceof HttpErrorResponse ? e.error?.description : 'Failed to load bed type';
      this.notification.error('Error', errorMessage);
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
    if (!this.editForm.valid) return;

    this.isLoading.save = true;

    try {
      let iconRef = this.editForm.value.iconRef;

      if (this.selectedFile) {
        const uploadRes = await firstValueFrom(
          this.fileService.uploadFile(this.selectedFile, this.selectedFile.name)
        );
        iconRef = uploadRes.urlPath;
      }

      await firstValueFrom(
        this.bedTypeService.saveBedType({
          id: this.bedType?.id,
          name: this.editForm.value.name!,
          iconRef: iconRef!,
        })
      );

      this.notification.success('Success', 'Bed type has been saved successfully!');
      this.router.navigate(['/beds']);
    } catch (e: unknown) {
      const errorMessage = e instanceof HttpErrorResponse ? e.error?.description : 'Failed to save bed type';
      this.notification.error('Error', errorMessage);
    } finally {
      this.isLoading.save = false;
    }
  }

  cancel(): void {
    this.router.navigate(['/beds']);
  }
}
