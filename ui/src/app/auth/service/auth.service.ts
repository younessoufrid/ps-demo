import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { AuthRequest, AuthResponse } from '../../shared/models/beans';
import {
  AUTH_ROUTE,
  BASE_ROUTE,
  TOKEN,
  USER_NAME,
} from '../../shared/commun/constants';
import { Router } from '@angular/router';
import { map } from 'rxjs/operators';
import {API_AUTH_LDAP, API_AUTH_IODC} from '../../shared/commun/PsvUri';
import {LoadingSpinnerService} from '../../shared/service/loading-spinner.service';
import {AbstractService} from '../../shared/service/abstract.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService extends AbstractService{

  constructor(private httpClient: HttpClient,
              private router: Router, private loadingService: LoadingSpinnerService) {
    super();
  }
  public authenticate(authRequest: AuthRequest) {
    this.loadingService.setLoading(true, `${this.API_ROUTES.base() + API_AUTH_IODC}`);
    return this.httpClient.post<AuthResponse>(`${this.API_ROUTES.base() + API_AUTH_IODC}`,
      authRequest)
      .pipe(map((response) => {
        this.saveAuthenticationInfo(response);
        return true;
    }));
  }

  public logout() {
    localStorage.clear();
    this.router.navigate([`${BASE_ROUTE}/${AUTH_ROUTE}`]);
  }
  public isAuthenticated() {
    return (this.getToken() && this.getUserName() && !this.isExpired());
  }
  public saveAuthenticationInfo(info: AuthResponse) {
    localStorage.setItem(TOKEN, JSON.stringify(info.token));
    localStorage.setItem(USER_NAME, JSON.stringify(info.username));
  }

  public getUserName(): string {
    return localStorage.getItem(USER_NAME) !== null ? JSON.parse(localStorage.getItem(USER_NAME)) : null;
  }

  public getToken() {
    return (localStorage.getItem(TOKEN) !== null && localStorage.getItem(TOKEN).length > 0 ) ?
      JSON.parse(localStorage.getItem(TOKEN)) : null;
  }



  isExpired() {
    const expiry = (JSON.parse(atob(this.getToken().split('.')[1]))).exp;
    return (Math.floor((new Date()).getTime() / 1000)) >= expiry;
  }
  public hasRole(role: string) {
    return  true;
  }

}
