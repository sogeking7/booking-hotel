import { Component, inject } from '@angular/core';
import { FileService } from  '@lib/booking-hotel-api';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
})
export class FileUploadComponent {
  selectedFile?: File;
  isLoading = false;

  // Инжектируем сервис FileService для работы с загрузкой файлов
  // Этот сервис должен быть сгенерирован с помощью OpenAPI Generator
  private fileService = inject(FileService);

  constructor() {}

  onFileSelected(event: Event) {
    const target = event.target as HTMLInputElement;
    if (target.files?.length) {
      this.selectedFile = target.files[0];
    }
  }

  onUpload() {
    if (!this.selectedFile) {
      return;
    }

    this.isLoading = true;

    // Вызываем новый метод из сгенерированного сервиса,
    // передавая файл и его имя как два отдельных аргумента
    this.fileService.uploadFile(this.selectedFile, this.selectedFile.name)
      .subscribe({
        next: (response) => {
          this.isLoading = false;
          console.log('Файл успешно загружен!', response);
          alert('Файл успешно загружен!');
        },
        error: (err) => {
          this.isLoading = false;
          console.error('Ошибка при загрузке файла', err);
          alert('Ошибка при загрузке файла.');
        }
      });
  }
}
