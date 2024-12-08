import {ErrorHandler, Inject, Injectable, Injector} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {TranslateService} from '@ngx-translate/core';

@Injectable()
export class GlobalErrorHandlerService implements ErrorHandler {

  constructor(@Inject(Injector) private injector: Injector,
              private translateService: TranslateService) {
  }

  private get toasterService(): ToastrService {
    return this.injector.get(ToastrService);
  }

  handleError(error: any) {
    this.toasterService.error(this.translateService
      .instant('MESSAGE.ERROR.UNEXPECTED_ERROR'));
    throw error;
  }
}
