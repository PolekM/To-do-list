import { HttpInterceptorFn } from '@angular/common/http';
import { AuthenticationService } from '../Services/authentication.service';
import { inject } from '@angular/core';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
  const authenticationService = inject(AuthenticationService);
  const token = authenticationService.getToken();
  if(token!=''){
    req = req.clone({
      setHeaders:{
        Authorization: token
      }
    })
  }
  return next(req);
};
