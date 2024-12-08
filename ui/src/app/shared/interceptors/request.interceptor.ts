import {Injectable} from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpResponse, HttpErrorResponse, HttpStatusCode,
} from '@angular/common/http';
import {Observable, of, throwError} from 'rxjs';
import {LoadingSpinnerService} from '../service/loading-spinner.service';
import {catchError, tap} from 'rxjs/operators';
import {AuthService} from '../../auth/service/auth.service';
import {ToastrService} from 'ngx-toastr';
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {
  constructor(private loadingSpinnerService: LoadingSpinnerService,
              private authService: AuthService,
              private toaster: ToastrService,
              private translateService: TranslateService) {
  }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
      .pipe(catchError((err) => {
        this.loadingSpinnerService.setLoading(false, request.url);
        this.loadingSpinnerService.loadingIndicator.next(false);
        return this.handleError(err);
      }))
      .pipe(tap((event: HttpEvent<any>) => {
        if (event instanceof HttpResponse) {
          this.loadingSpinnerService.setLoading(false, request.url);
          this.loadingSpinnerService.loadingIndicator.next(false);
        }
        return event;
      }));
  }

  private handleError(error: any) {
    let handled = false;
    if (error instanceof HttpErrorResponse) {
      if (error.error instanceof ErrorEvent) {
        this.toaster.error(`${this.translateService.instant('MESSAGE.ERROR')}: ${error.error.message}`);
        handled = true;
      } else {
        switch (error.status) {
          case 0:
            this.toaster.error(this.translateService.instant('MESSAGE.ERROR.CONNECTION_ERROR'));
            break;
          case HttpStatusCode.Unauthorized:
            this.toaster.info(this.translateService.instant('MESSAGE.WARNING.SESSION_EXPIRED'));
            this.authService.logout();
            handled = true;
            break;
          case HttpStatusCode.Forbidden:
            this.toaster.info(this.translateService.instant('MESSAGE.WARNING.SESSION_EXPIRED'));
            this.authService.logout();
            handled = true;
            break;
          default:
            if (error.status >= HttpStatusCode.InternalServerError) {
              this.toaster.error(this.translateService.instant('MESSAGE.ERROR.SERVER_ERROR'));
              handled = true;
            }
            break;
        }
      }
    } else {
      this.toaster.error(this.translateService.instant('MESSAGE.ERROR.UNEXPECTED_ERROR'));
      handled = true;
    }
    if (handled) {
      return of(error);
    } else {
      return throwError(error);
    }
  }
}
