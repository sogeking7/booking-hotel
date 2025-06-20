import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';
import { routes } from './app.routes';
import { icons } from './icons-provider';
import { provideNzIcons } from 'ng-zorro-antd/icon';
import { en_US, provideNzI18n } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import {provideHttpClient, withFetch, withInterceptors} from '@angular/common/http';
import { authInterceptor } from './interceptors/auth.interceptor';
import { BASE_PATH } from '../lib/booking-hotel-api';
import { ApiModule, Configuration } from '../lib/booking-hotel-api';

import { importProvidersFrom } from '@angular/core';

registerLocaleData(en);

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideNzIcons(icons),
    provideNzI18n(en_US),
    provideAnimationsAsync(),
    provideHttpClient(
      withFetch(),
      withInterceptors([authInterceptor])),



    {
      provide: BASE_PATH,
      useValue: '',
    },
    importProvidersFrom(
      ApiModule.forRoot(() => new Configuration({
        basePath: 'http://localhost:8080',
        withCredentials: true,
      }))
    )
  ]
};
