import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
// Импорты для Ant Design
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';
import { NzTypographyModule } from 'ng-zorro-antd/typography';
import { NzNotificationService } from 'ng-zorro-antd/notification';
import { NzModalModule, NzModalService } from 'ng-zorro-antd/modal';
// Импорты вашего сгенерированного API
import { BedTypeModel, BedTypeService } from '@lib/booking-hotel-api';


@Component({
  selector: 'app-beds-page',
  standalone: true,
  imports: [
    CommonModule,
    RouterLink,
    NzTableModule,
    NzButtonModule,
    NzIconModule,
    NzPopconfirmModule,
    NzTypographyModule,
    NzModalModule,
  ],
  templateUrl: './beds.page.html',
})
export class BedsPage implements OnInit {
  bedTypes: BedTypeModel[] = [];
  isLoading = {
    bedTypes: false,
    delete: false,
  };

  private bedTypeService = inject(BedTypeService);
  private notification = inject(NzNotificationService);
  private modal = inject(NzModalService);
  private http = inject(HttpClient);

  ngOnInit() {
    this.loadBedTypes();
  }

  async loadBedTypes() {
    this.isLoading.bedTypes = true;
    try {
      this.bedTypes = await firstValueFrom(this.bedTypeService.getAllBedTypes());
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to load bed types');
    } finally {
      this.isLoading.bedTypes = false;
    }
  }

  async showPreview(imageUrl: string): Promise<void> {
    const fileName = imageUrl.split('/').pop();
    if (!fileName) {
      this.notification.error('Ошибка', 'Некорректное имя файла.');
      return;
    }
    // ИЗМЕНЕНО: Добавлен префикс /api к URL
    const downloadUrl = `http://localhost:8080/api/core/files/download/${fileName}`;

    try {
      const blob = await firstValueFrom(this.http.get(downloadUrl, { responseType: 'blob', withCredentials: true }));
      const objectUrl = URL.createObjectURL(blob);

      this.modal.create({
        nzTitle: 'Просмотр иконки',
        nzContent: `<div class="flex justify-center"><img src="${objectUrl}" style="max-width: 100%;" alt="Icon preview"/></div>`,
        nzFooter: null,
        nzOnCancel: () => URL.revokeObjectURL(objectUrl)
      });
    } catch (error) {
      this.notification.error('Ошибка', 'Не удалось загрузить изображение для предпросмотра.');
      console.error('Preview image load error:', error);
    }
  }

  async showDetails(bed: BedTypeModel): Promise<void> {
    const fileName = bed.iconRef?.split('/').pop();
    if (!fileName) {
      this.notification.error('Ошибка', 'У этого элемента нет иконки.');
      return;
    }

    // ИЗМЕНЕНО: Добавлен префикс /api к URL
    const downloadUrl = `http://localhost:8080/api/core/files/download/${fileName}`;
    let imageContent = '<p>Не удалось загрузить иконку.</p>';
    let objectUrl: string | null = null;

    try {
      const blob = await firstValueFrom(this.http.get(downloadUrl, { responseType: 'blob', withCredentials: true }));
      objectUrl = URL.createObjectURL(blob);
      imageContent = `<img src="${objectUrl}" style="max-width: 150px; border-radius: 4px; border: 1px solid #f0f0f0;" alt="Icon"/>`;
    } catch (error) {
      console.error('Details image load error:', error);
    }

    this.modal.create({
      nzTitle: `Детали для: ${bed.name}`,
      nzContent: `
        <div class="py-4">
          <p class="mb-2"><strong>ID:</strong> ${bed.id}</p>
          <p class="mb-4"><strong>Название:</strong> ${bed.name}</p>
          <p><strong>Иконка:</strong></p>
          ${imageContent}
        </div>
      `,
      nzFooter: [{ label: 'Закрыть', type: 'default', onClick: () => this.modal.closeAll() }],
      nzOnCancel: () => {
        if (objectUrl) {
          URL.revokeObjectURL(objectUrl);
        }
      }
    });
  }

  async deleteBedType(id: number) {
    this.isLoading.delete = true;
    try {
      await firstValueFrom(this.bedTypeService.deleteBedTypeById(id));
      this.notification.success('Success', 'Bed type has been deleted.');
      await this.loadBedTypes();
    } catch (e) {
      const error = e as HttpErrorResponse;
      this.notification.error('Error', error.error?.description || 'Failed to delete bed type.');
    } finally {
      this.isLoading.delete = false;
    }
  }
}
