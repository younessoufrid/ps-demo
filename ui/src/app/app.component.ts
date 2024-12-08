import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {TranslateService} from '@ngx-translate/core';
import {AppConfigService} from './shared/service/app-config.service';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from './auth/service/auth.service';
import {BASE_ROUTE, ERROR_ROUTE, HOME_ROUTE, LOAD_CONFIG_FILE_ERROR, VERSION} from './shared/commun/constants';
import {LoadingSpinnerService} from './shared/service/loading-spinner.service';
import {delay} from 'rxjs/operators';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  hideMenu = true;
  isLoading = false;
  version = '';

  constructor(private router: Router,
              private toaster: ToastrService,
              private translateService: TranslateService,
              private appConfigService: AppConfigService,
              protected authService: AuthService,
              private loadingSpinnerService: LoadingSpinnerService) {
    translateService.setDefaultLang('fr');
    const lang = navigator.language ? navigator.language.slice(0, 2) : 'fr';
    translateService.use(lang);
  }

  ngOnInit(): void {
    this.listenToLoading();
    if (!this.appConfigService.isConfigLoaded()) {
      this.toaster.error(LOAD_CONFIG_FILE_ERROR);
      this.router.navigate([ERROR_ROUTE]);
      return;
    }
    this.setVersion();
  }

  checkMenuState() {
    const url = window.location.href;
    this.hideMenu =  (url.includes('auth') || url.includes('page-not-found'));
  }

  /**
   * Listen to the loadingSub property in the LoadingService class. This drives the
   * display of the loading spinner.
   */
  listenToLoading(): void {
    this.loadingSpinnerService.loadingSub
      .pipe(delay(0)) // This prevents a ExpressionChangedAfterItHasBeenCheckedError for subsequent requests
      .subscribe((loading) => {
        this.isLoading = loading;
      });
  }
  logout() {
    this.authService.logout();
  }

  get user() {
    const username: string = this.authService.getUserName();
    return username ? username.slice(0, 2).toUpperCase() : 'UK';
  }

  private setVersion() {
    this.version = this.appConfigService.getConfig(VERSION);
  }

  isShowSidebarNavigation(): boolean {
    return window.location.pathname !== `/${BASE_ROUTE}/${HOME_ROUTE}`;
  }
}
