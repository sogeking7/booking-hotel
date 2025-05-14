import {Injectable, PLATFORM_ID, Inject} from '@angular/core';
import {isPlatformBrowser} from '@angular/common';
import {tap} from 'rxjs/operators';
import {AuthService, SignInRequest} from '../../lib/booking-hotel-api';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private readonly isBrowser: boolean;

  constructor(
    @Inject(PLATFORM_ID) private platformId: Object,
    private authService: AuthService,
  ) {
    this.isBrowser = isPlatformBrowser(this.platformId);
  }

  login(username: string, password: string) {
    const signInRequest: SignInRequest = {
      email: username,
      password: password
    };

    return this.authService.apiAuthSignInPost(signInRequest)
      .pipe(
        tap(jwtModel => {
          this.setToken(jwtModel.accessToken);
        })
      );
  }


  private getFromStorage(key: string): string | null {
    if (this.isBrowser) {
      return localStorage.getItem(key);
    }
    return null;
  }

  private setToStorage(key: string, value: string): void {
    if (this.isBrowser) {
      localStorage.setItem(key, value);
    }
  }

  private removeFromStorage(key: string): void {
    if (this.isBrowser) {
      localStorage.removeItem(key);
    }
  }

  isAuthenticated(): boolean {
    const token = this.getToken();
    return !!token;
  }

  getToken(): string | null {
    return this.getFromStorage('token');
  }

  setToken(token: string): void {
    this.setToStorage('token', token);
  }

  removeToken(): void {
    this.removeFromStorage('token');
  }

  logout(): void {
    this.removeToken();
  }
}
