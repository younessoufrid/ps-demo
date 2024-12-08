import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import {Observable} from 'rxjs';
import {AuthService} from '../../auth/service/auth.service';
import {Router} from '@angular/router';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private router: Router) {
  }

  addToken(req: HttpRequest<any>, token: string): HttpRequest<any> {
    if (token) {
      req = req.clone({setHeaders: {Authorization: `Bearer ${token}`}});
    }
    return req;
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (this.authService.isAuthenticated()) {
      return next.handle(this.addToken(request, this.authService.getToken()));
    } else {
       return next.handle(request);
    }
  }
}
