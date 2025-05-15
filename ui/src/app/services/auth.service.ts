import {Injectable} from '@angular/core';
import {AuthService} from '../../lib/booking-hotel-api';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private authService: AuthService,
  ) {
  }

  login(username: string, password: string) {
    return this.authService.apiAuthSignInPost({
      email: username,
      password: password
    }).pipe();
  }

  async isAuthenticated(): Promise<boolean> {
    return (await true);
  }

  async logout() {

  }
}
