import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthenticationService } from '../Services/authentication.service';

export const authGuard: CanActivateFn = (route, state) => {
  const router = inject(Router)
  const authenticationService = inject(AuthenticationService)
  if(authenticationService.getToken()!=''){
    return true;
  }
  else{
    router.navigate(['/login']);
    return false;
  }
};
