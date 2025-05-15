import {Injectable} from '@angular/core';
import {AuthService, UserService, UserSession} from '../../lib/booking-hotel-api';
import {lastValueFrom, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  userSession: UserSession | null = null;

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
        this.userSession = await lastValueFrom(this.userService.getMe())
        console.log("userSession", this.userSession);
        return true;
      } catch {
        this.userSession = null;
        return false;
      }
  }

  async logout() {

  }
}
