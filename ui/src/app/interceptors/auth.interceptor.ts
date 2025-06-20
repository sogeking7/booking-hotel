import {HttpInterceptor, HttpRequest, HttpHandler, HttpInterceptorFn} from '@angular/common/http';


export const authInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req); // ничего не добавляем
};
