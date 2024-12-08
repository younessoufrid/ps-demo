import {Component, OnInit} from '@angular/core';
import {RECAP_TABLE_COLUMN, SUMMARY} from '../../shared/commun/constants';
import {OrderDataResponse, OrderFilters, OrderFiltersData, SummaryOrderLines,} from '../../shared/models/beans';
import {map} from 'rxjs/operators';
import {OrderService} from '../saisie/service/order.service';
import { setCurrentMonthName} from '../../shared/utils/dateUtils';
import {NgbModal, NgbModalRef} from '@ng-bootstrap/ng-bootstrap';
import {ConfirmationComponent} from '../../shared/components/confirmation/confirmation.component';
import {SaisieService} from '../saisie/service/saisie.service';
import {ToastrService} from 'ngx-toastr';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-summary',
  templateUrl: './summary.component.html',
  styleUrls: ['./summary.component.scss']
})
export class SummaryComponent implements OnInit {
  currentPage = 1;
  rowsPerpage = 10;
  totalRows = 0;
  isOrderSoumis= false;
  columns = RECAP_TABLE_COLUMN;
  rows: SummaryOrderLines[] = [];
  rowsToShow: SummaryOrderLines[] = [];
  filtersData: OrderFiltersData;
  currentMonth: string;
  days = [];
  lastDayOfMonth: number;
  modalRef: NgbModalRef;
  orderId: number;
  expend = false;


  constructor(private orderService: OrderService,private saisieService: SaisieService, private toaster: ToastrService,
              protected translateService: TranslateService, private modalService: NgbModal) {
  }

  public pageChange(event) {
    if (event) {
      this.currentPage = event;
      this.getRowTableToShow();
    }
  }

  public getRowTableToShow() {
    const startIndex = (this.rowsPerpage - 1) * this.currentPage - (this.rowsPerpage - 1);
    const endIndex = (this.rowsPerpage - 1) * this.currentPage;
    this.rowsToShow = this.rows?.slice(startIndex, endIndex);
    this.rowsToShow?.push(this.getTotalMonth());
  }

  ngOnInit(): void {
    this.orderService.orderData$.pipe(map(data => {
      return this.mapOrderDataToFiltersData(data);
    })).subscribe((data: OrderFiltersData) => {
      this.filtersData = data;
      this.isOrderSoumis = data.summary?.every(s => s.soumis === true);
      this.rows = this.filtersData.summary;
      this.totalRows = this.rows?.length;
      this.getRowTableToShow();
    });
  }

  private mapOrderDataToFiltersData(data: OrderDataResponse) {
    const filtersData: OrderFiltersData = {saleSites: [], cdrs: [], clients: [], affairs: [], orders: [], orderLines: [], summary: []};
    filtersData.saleSites = data?.saleSites?.map(ss => {
      return {label: ss.reference, value: ss.reference};
    });
    filtersData.cdrs = data?.cdrs?.map(cdr => {
      return {label: cdr.reference, value: cdr.reference};
    });
    filtersData.clients = data?.clients?.map(client => {
      return {label: client.name, value: client.id};
    });
    filtersData.affairs = data?.affairs?.map(affair => {
      return {label: affair.reference, value: affair.id};
    });
    filtersData.orders = data?.orders?.map(order => {
      return {label: order.number, value: order.id};
    });
    filtersData.summary = data?.summary?.map(summary => {
      return {
        designation: summary.designation,
        summaryItems: summary.summaryItems,
        totalMonthQte: summary.totalMonthQte,
        priceNetHT: summary.priceNetHT,
        uniteVente: summary.uniteVente,
        line: summary.line,
        totalPrice: summary.totalPrice,
        soumis: summary.soumis
      };
    });
    return filtersData;
  }

  searchByFilters(searchFilters: OrderFilters) {
    searchFilters.action = SUMMARY;
    this.currentPage = 1;
    this.days = [];
    const selectedDate = new Date();
    selectedDate.setFullYear(Number(searchFilters.date.split('/')[2]),  Number(searchFilters.date.split('/')[1]) - 1, 1);
    this.orderId = searchFilters.order;
    this.currentMonth = setCurrentMonthName(selectedDate.getMonth() + 1);
    this.lastDayOfMonth = new Date(selectedDate.getFullYear(), selectedDate.getMonth() + 1, 0).getDate();
    for (let i = 1; i <= this.lastDayOfMonth; i++) {
      let isWeekday = this.isWeekEndDay(new Date(selectedDate.getFullYear(), selectedDate.getMonth(), i));
      this.days.push({value: i + '-' + this.currentMonth, week_end: isWeekday});
    }
    this.orderService.fetchOrderData(searchFilters);
  }

  isWeekEndDay(date: Date): boolean {
    const dayOfWeek = date.getDay();
    return dayOfWeek === 0 || dayOfWeek === 6;
  }

  validateMonthQuantity() {
    this.modalRef = this.openpopup(ConfirmationComponent)
    this.modalRef.componentInstance.action = 'validate'
    this.modalRef.componentInstance.content =  {
      action: 'validate',
      params: {order: this.filtersData.orders.filter(order => order.value == this.orderId)[0].label}
    }
    this.modalRef.closed.subscribe(data => {
      if (data) {
        this.saisieService.validateMonthQuantity(this.orderId).subscribe(
          data => {
            this.toaster.success(`${this.translateService.instant('ORDER_VALIDATED.MESSAGE', {commande : this.filtersData.orders[0].label})}`);
            this.isOrderSoumis = data;
          }
        );
      }
    });
  }
  openpopup(component: any) {
    return this.modalService.open(component, {
      backdrop: 'static',
      keyboard: false,
    });
  }

  private getTotalMonth() {
      return {
        designation: undefined,
        summaryItems: [],
        line: undefined,
        priceNetHT: undefined,
        uniteVente: undefined,
        totalMonthQte: undefined,
        totalPrice: this.rows.reduce((pre, curr) => pre + curr.totalPrice, 0),
        soumis: false
      };
    }
}
