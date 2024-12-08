import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {OrderLine} from '../../../shared/models/beans';
import {Observable} from 'rxjs';
import {LoadingSpinnerService} from '../../../shared/service/loading-spinner.service';
import {
  API_HIDE_LINE, API_RESET_MONTH_QUANTITY,
  API_RESET_QUANTITY,
  API_SHOW_HIDDEN_LINES,
  API_UPDATE_QUANTITY,
  API_UPDATE_TEXT_LINE, API_VALIDATE_SAISIE
} from '../../../shared/commun/PsvUri';
import {AbstractService} from '../../../shared/service/abstract.service';

@Injectable({
  providedIn: 'root'
})
export class SaisieService extends AbstractService{

  constructor(private httpClient: HttpClient, private loadingSpinnerService: LoadingSpinnerService) {
    super();
  }

  public updateQuantity(row: OrderLine): Observable<OrderLine> {
    this.loadingSpinnerService.loadingIndicator.next(true);
    return this.httpClient.put<OrderLine>(`${this.API_ROUTES.base() + API_UPDATE_QUANTITY}`, row);
  }

  public hideLine(row: OrderLine): Observable<OrderLine> {
    this.loadingSpinnerService.loadingIndicator.next(true);
    return this.httpClient.put<OrderLine>(`${this.API_ROUTES.base() + API_HIDE_LINE}`, row);
  }

  public resetQuantity(row: OrderLine): Observable<OrderLine> {
    this.loadingSpinnerService.loadingIndicator.next(true);
    return this.httpClient.put<OrderLine>(`${this.API_ROUTES.base() + API_RESET_QUANTITY}`, row);
  }

  ShowHidenLines(ordeLine) {
    this.loadingSpinnerService.loadingIndicator.next(true);
    return this.httpClient.put<number>(`${this.API_ROUTES.base() + API_SHOW_HIDDEN_LINES}`, ordeLine);
  }


  updateTextLigne(row: OrderLine) {
    this.loadingSpinnerService.loadingIndicator.next(true);
    return this.httpClient.put<OrderLine>(`${this.API_ROUTES.base() + API_UPDATE_TEXT_LINE}`, row);
  }

  resetCommandeQuantity(orderLine): Observable<OrderLine[]> {
    this.loadingSpinnerService.loadingIndicator.next(true);
    return this.httpClient.put<OrderLine[]>(`${this.API_ROUTES.base() + API_RESET_MONTH_QUANTITY}`, orderLine );
  }

  validateMonthQuantity(orderId): Observable<boolean> {
    this.loadingSpinnerService.loadingIndicator.next(true);
    return this.httpClient.put<boolean>(`${this.API_ROUTES.base() + API_VALIDATE_SAISIE}/`+orderId, {} );
  }

}
