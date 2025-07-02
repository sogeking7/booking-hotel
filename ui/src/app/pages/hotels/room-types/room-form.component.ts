import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzSelectModule} from 'ng-zorro-antd/select';
import {NzInputNumberModule} from 'ng-zorro-antd/input-number';
import {HotelRoomsService, Room} from './HotelRoomsService';
import {BedTypeService} from '../../../../lib/booking-hotel-api/api/bed-type.service';
import {BedTypeModel} from '../../../../lib/booking-hotel-api/model/bed-type-model';
import {firstValueFrom} from 'rxjs';
import {FacilityModel} from '../../../../lib/booking-hotel-api/model/facility-model';


export interface RoomTypeModel {
  id: number;
  hotelId: number;
  bedTypeId: number;
  name: string;
  count: number;
  bedType?: BedTypeModel;
  facilities?: FacilityModel[];
}


@Component({
  selector: 'app-room-form',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    NzFormModule,
    NzInputModule,
    NzButtonModule,
    NzSelectModule,
    NzInputNumberModule
  ],
  template: `
    <h2>{{ isEditMode ? 'Редактировать комнату' : 'Добавить комнату' }}</h2>
    <form [formGroup]="form" (ngSubmit)="submit()">
      <div class="form-group">
        <label>Name</label>
        <input nz-input formControlName="name" />
      </div>

      <div class="form-group">
        <label>BedType</label>
        <nz-select formControlName="bedTypeId" nzPlaceHolder="Выберите тип кровати">
          <nz-option *ngFor="let bedType of bedTypes" [nzValue]="bedType.id" [nzLabel]="bedType.name"></nz-option>
        </nz-select>
      </div>

      <div class="form-group">
        <label>Count</label>
        <nz-input-number formControlName="count" [nzMin]="1" [nzMax]="100"></nz-input-number>
      </div>

      <div class="form-group">
        <label>Price</label>
        <nz-input-number formControlName="price" [nzMin]="0" [nzStep]="1000"></nz-input-number>
      </div>

      <button nz-button nzType="primary" [disabled]="form.invalid">Сохранить</button>
      <button nz-button (click)="cancel()">Отмена</button>
    </form>
  `,
  styles: [`
    .form-group {
      margin-bottom: 16px;
    }
    button {
      margin-right: 8px;
    }
  `]
})
export class RoomFormComponent implements OnInit {
  form!: FormGroup;
  hotelId!: number;
  roomId?: number;
  isEditMode = false;
  bedTypes: BedTypeModel[] = [];
  loading = false;

  private fb = inject(FormBuilder);
  private roomsService = inject(HotelRoomsService);
  private bedTypeService = inject(BedTypeService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  ngOnInit(): void {
    this.hotelId = +this.route.snapshot.paramMap.get('id')!;
    this.roomId = this.route.snapshot.paramMap.get('roomId') ? +this.route.snapshot.paramMap.get('roomId')! : undefined;
    this.isEditMode = !!this.roomId;

    this.form = this.fb.group({
      name: ['', Validators.required],
      bedTypeId: [null, Validators.required],
      count: [1, [Validators.required, Validators.min(1)]],
      price: [0, [Validators.required, Validators.min(0)]]
    });

    this.loadBedTypes();

    if (this.isEditMode && this.roomId) {
      this.loadRoomData(this.roomId);
    }
  }

  async loadBedTypes(): Promise<void> {
    try {
      this.bedTypes = await firstValueFrom(this.bedTypeService.getAllBedTypes());
    } catch (error) {
      console.error('Error loading bed types:', error);
    }
  }

  async loadRoomData(roomId: number): Promise<void> {
    this.loading = true;
    try {
      // Get the room data
      const room = await this.roomsService.getRoomById(this.hotelId, roomId);

      // Convert price back to count for the form
      const count = Math.floor(room.price / 1000);

      // We need to get the room type details to get the bedTypeId
      const rooms = await this.roomsService.getRoomByHotelId(this.hotelId);
      const roomDetails = rooms.find(r => r.id === roomId);

      if (roomDetails) {
        this.form.patchValue({
          name: room.type,
          count: count,
          price: room.price,
          // We're setting a default bedTypeId of 1 here, but in a real app
          // you would get the actual bedTypeId from the room details
          bedTypeId: roomDetails.bedTypeId || 1,
        });
      }
    } catch (error) {
      console.error('Error loading room data:', error);
    } finally {
      this.loading = false;
    }
  }

  async submit(): Promise<void> {
    if (this.form.valid) {
      const formValue = this.form.value;

      // Create a Room object from the form data
      const room: Room = {
        id: this.roomId || 0,
        type: formValue.name,
        price: formValue.price,
        bedTypeId: formValue.bedTypeId,
        description: '' // This will be generated by the service
      };

      try {
        await this.roomsService.saveRoom(this.hotelId, room);
        this.router.navigate(['/hotels', this.hotelId, 'rooms']);
      } catch (error) {
        console.error('Error saving room:', error);
      }
    }
  }

  cancel(): void {
    this.router.navigate(['/hotels', this.hotelId, 'rooms']);
  }
}
