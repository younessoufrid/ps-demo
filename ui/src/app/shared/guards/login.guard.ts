import {CanActivateFn, Router} from '@angular/router';
import {AuthService} from '../../auth/service/auth.service';
import {inject} from '@angular/core';
import {BASE_ROUTE, HOME_ROUTE, SAISIE_ROUTE} from '../commun/constants';


export const LoginGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  if (authService.isAuthenticated()) {
    router.navigate([`${BASE_ROUTE}/${HOME_ROUTE}`]);
    return false;
  }
  return true;
};
