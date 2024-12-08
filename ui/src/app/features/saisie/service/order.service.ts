import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BehaviorSubject} from 'rxjs';
import {OrderDataResponse, OrderFilters} from '../../../shared/models/beans';
import {API_FILTER} from '../../../shared/commun/PsvUri';
import {AbstractService} from '../../../shared/service/abstract.service';

@Injectable({
  providedIn: 'root'
})
export class OrderService extends AbstractService{

  orderDataSubject = new BehaviorSubject<OrderDataResponse>({
    saleSites: [],
    cdrs: [],
    clients: [],
    affairs: [],
    orders: [],
    orderLines: [],
    summary: []
  });
  orderData$ = this.orderDataSubject.asObservable();

  constructor(private http: HttpClient) {
    super();
  }

  fetchOrderData(filters: OrderFilters) {
    this.http.post<any>(`${this.API_ROUTES.base() + API_FILTER}`, filters).subscribe((data: OrderDataResponse) => {
      this.orderDataSubject.next(data);
    });
  }
}
