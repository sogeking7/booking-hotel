import {Injectable} from '@angular/core';
import {AuthService, GetMeResponse, UserService} from '../../lib/booking-hotel-api';
import {lastValueFrom, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  user: GetMeResponse | null = null;

  constructor(
    private authService: AuthService,
    private userService: UserService,
  ) {
  }

  login(username: string, password: string) {
    return this.authService.signIn({
      email: username,
      password: password
    }).pipe();
  }

  async isAuthenticated() {
    try {
      this.user = await lastValueFrom(this.userService.getMe())
      console.log("userSession", this.user);
      return true;
    } catch {
      this.user = null;
      return false;
    }
  }

  async logout() {

  }
}
