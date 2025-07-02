import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CommonModule} from '@angular/common';
import {NzTableModule} from 'ng-zorro-antd/table';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {NzPopconfirmModule} from 'ng-zorro-antd/popconfirm';
import {NzMessageService} from 'ng-zorro-antd/message';
import {HotelRoomsService, Room} from './HotelRoomsService'; // сервис для загрузки комнат

@Component({
  selector: 'app-rooms',
  standalone: true,
  imports: [CommonModule, NzTableModule, NzButtonModule, NzPopconfirmModule],
  template: `
    <div class="header-actions">
      <h2>Комнаты отеля ID: {{ hotelId }}</h2>
      <button nz-button nzType="primary" (click)="addRoom()">Добавить комнату</button>
    </div>
    <nz-table
      [nzData]="rooms"
      [nzBordered]="true"
      [nzLoading]="loading"
      [nzSize]="'small'"
    >
      <thead>
        <tr>
          <th>ID</th>
          <th>Type</th>
          <th>Price</th>
          <th>Facilities</th>
          <th>Actions</th>
        </tr>
      </thead>
      <tbody>
        <tr *ngFor="let room of rooms">
          <td>{{ room.id }}</td>
          <td>{{ room.type }}</td>
          <td>{{ room.price }} ₸</td>
          <td>{{ room.description }}</td>
          <td>
            <button nz-button nzType="primary" nzSize="small" (click)="editRoom(room.id)">Edit</button>
            <button
              nz-button
              nzType="default"
              nzSize="small"
              nzDanger
              nz-popconfirm
              nzPopconfirmTitle="Are you sure you want to delete this room?"
              nzPopconfirmPlacement="top"
              (nzOnConfirm)="deleteRoom(room.id)"
            >
              Delete
            </button>
            <button
              nz-button
              nzType="primary"
              nzSize="small"
              (click)="bookRoom(room.id)"
            >
              Book Now
            </button>
          </td>
        </tr>
      </tbody>
    </nz-table>
  `,
  styles: [`
    .header-actions {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 16px;
    }
    button {
      margin-right: 8px;
    }
  `]
})
export class RoomsComponent implements OnInit {
  hotelId!: number;
  rooms: Room[] = [];
  loading = false;

  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private roomsService = inject(HotelRoomsService);
  private message = inject(NzMessageService);

  ngOnInit(): void {
    this.hotelId = +this.route.snapshot.paramMap.get('id')!;
    this.fetchRooms();
  }

  async fetchRooms(): Promise<void> {
    this.loading = true;
    try {
      this.rooms = await this.roomsService.getRoomByHotelId(this.hotelId);
    } catch (error) {
      console.error('Ошибка загрузки комнат:', error);
    } finally {
      this.loading = false;
    }
  }

  addRoom(): void {
    this.router.navigate(['/hotels', this.hotelId, 'rooms', 'new']);
  }

  editRoom(roomId: number): void {
    this.router.navigate(['/hotels', this.hotelId, 'rooms', roomId, 'edit']);
  }

  async deleteRoom(roomId: number): Promise<void> {
    this.loading = true;
    try {
      await this.roomsService.deleteRoom(this.hotelId, roomId);
      this.message.success('Room deleted successfully');
      await this.fetchRooms(); // Refresh the list
    } catch (error) {
      console.error('Error deleting room:', error);
      this.message.error('Failed to delete room');
    } finally {
      this.loading = false;
    }
  }

  bookRoom(roomTypeId: number): void {
    this.router.navigate(['/orders'], {
      queryParams: {
        hotelId: this.hotelId,
        roomTypeId: roomTypeId
      }
    });
  }
}
