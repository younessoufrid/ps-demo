import { BrowserModule } from '@angular/platform-browser';
import {APP_INITIALIZER, ErrorHandler, LOCALE_ID, NgModule} from '@angular/core';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {SharedModule} from './shared/shared.module';
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {ToastrModule} from 'ngx-toastr';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {TranslateModule, TranslateLoader} from '@ngx-translate/core';
import {HttpClientModule, HttpClient, HTTP_INTERCEPTORS} from '@angular/common/http';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {RequestInterceptor} from './shared/interceptors/request.interceptor';
import {AppConfigService} from './shared/service/app-config.service';
import {AuthInterceptor} from './shared/interceptors/auth.interceptor';
import {TooltipModule} from 'ngx-bootstrap/tooltip';
import {GlobalErrorHandlerService} from './shared/service/global-error-handler.service';
import localeFr from '@angular/common/locales/fr';
import {registerLocaleData} from '@angular/common';
registerLocaleData(localeFr);
@NgModule({
  declarations: [
    AppComponent,
    PageNotFoundComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    SharedModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(
      {
        timeOut: 6000,
        positionClass: 'toast-top-right',
        preventDuplicates: true,
        closeButton: false,
        maxOpened: 1,
        tapToDismiss: true,
      }
    ),
    TranslateModule.forRoot(
      {
        loader: {
          provide: TranslateLoader,
          useFactory: HttpLoaderFactory,
          deps: [HttpClient]
        },
      }
    ),
    AppRoutingModule,
    TooltipModule
  ],
  exports: [],
  providers: [
    {provide: APP_INITIALIZER, useFactory: (config: AppConfigService) => () => config.loadConfig(), deps: [AppConfigService], multi: true},
    {provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi : true},
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi : true},
    {provide: ErrorHandler, useClass: GlobalErrorHandlerService},
    {provide: LOCALE_ID, useValue: 'fr-FR'}
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
export function HttpLoaderFactory(http: HttpClient) {
  return new TranslateHttpLoader(http, './assets/i18n/', '.json');
}
