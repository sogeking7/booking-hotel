import {Component, inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NzFormModule} from 'ng-zorro-antd/form';
import {NzInputModule} from 'ng-zorro-antd/input';
import {NzButtonModule} from 'ng-zorro-antd/button';
import {HotelsService} from '../HotelsService';

@Component({
  selector: 'app-hotel-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, NzFormModule, NzInputModule, NzButtonModule],
  template: `
    <h2>Редактировать отель</h2>
    <form [formGroup]="form" (ngSubmit)="submit()">
      <label>Название</label>
      <input nz-input formControlName="name"/><br/><br/>

      <label>Адрес</label>
      <input nz-input formControlName="address"/><br/><br/>

      <label>Телефон</label>
      <input nz-input formControlName="phone"/><br/><br/>

      <button nz-button nzType="primary" [disabled]="form.invalid">Сохранить</button>
    </form>
  `
})
export class HotelFormComponent implements OnInit {
  form!: FormGroup;
  hotelId!: number;

  private fb = inject(FormBuilder);
  private hotelsService = inject(HotelsService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);

  ngOnInit(): void {
    this.hotelId = +this.route.snapshot.paramMap.get('id')!;
    this.form = this.fb.group({
      name: ['', Validators.required],
      address: [''],
      phone: ['']
    });


  }

  private async loadHotelData() {

    try {
      const res = await this.hotelsService.getHotelById(this.hotelId)
    } catch (e) {

    } finally {

    }
  }

  async submit() {
    if (!this.form.valid) {
      return;
    }
    try {
      await this.hotelsService.updateHotel(this.hotelId, this.form.value)
    } catch (e) {

    } finally {
      await this.router.navigate(['/hotels']);
    }
  }
}

