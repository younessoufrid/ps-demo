import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ColumnMode, id} from '@swimlane/ngx-datatable';
import {OrderLine} from '../../models/beans';
import {LoadingSpinnerService} from '../../service/loading-spinner.service';
import {document} from 'ngx-bootstrap/utils';

@Component({
  selector: 'psv-table',
  templateUrl: './psv-table.component.html',
  styleUrls: ['./psv-table.component.scss']
})
export class PsvTableComponent implements OnInit {
  SAISIE_PATTERN = /^-?\d+(\.\d+)?(\s*\+\s*-?\d+(\.\d+)?)*$/;
  @Input() isOrderValide = false;
  @Input() columns: any[];
  @Input() rows: OrderLine[];
  @Input() headerHeight: number;
  @Input() footerHeight: number;
  @Input() rowHeight: number;
  @Input() loadingIndicator = false;
  @Input() scrollbarH = false;
  @Input() scrollbarV = false;
  @Input() totalRows: number;
  @Input() currentPage: number;
  @Input() rowsPerPage: number;
  @Output() pageChange: EventEmitter<number> = new EventEmitter();
  @Output() rowQuantityEmiter: EventEmitter<OrderLine> = new EventEmitter();
  @Output() resetQuantityEmiter: EventEmitter<OrderLine> = new EventEmitter();
  @Output() resetCommandeQuantityEmiter: EventEmitter<boolean> = new EventEmitter();
  @Output() hideLineEmiter: EventEmitter<OrderLine> = new EventEmitter();
  @Output() editTextLineEmiter: EventEmitter<OrderLine> = new EventEmitter();
  @Output() hideLinesEmiter: EventEmitter<void> = new EventEmitter();

  ColumnMode = ColumnMode;


  constructor(private loadingSpinnerService: LoadingSpinnerService) {
  }

  ngOnInit(): void {
    this.columns = this.columns?.sort((a, b) => a.order - b.order);
    this.loadingSpinnerService.loadingIndicator.subscribe(value => this.loadingIndicator = value);
    this.isOrderValide = this.rows.filter(item => item.soumis !== undefined).every(item => item.soumis === true);

  }

  onPage(event) {
    if (event && this.currentPage !== event) {
      this.currentPage = event.page;
      this.pageChange.emit(this.currentPage);
    }
  }

  updateQuantity(newQte, row: OrderLine) {
    if (this.validateNewQuantity(newQte, row.id)) {
      const currentIndex = this.rows.findIndex(item => item.id === row.id);
      row.quantity = this.calculateSumQuantity(newQte);
      this.rowQuantityEmiter.emit(row);
      if (currentIndex < (this.rows.length - 2) && currentIndex !== -1) {
        document.getElementById(String(this.rows[currentIndex + 1].id)).focus();
      }
    }
  }

  resetQuantity(row: OrderLine) {
    this.resetQuantityEmiter.emit(row);
  }
  resetCommandeQuantity() {
    this.resetCommandeQuantityEmiter.emit(true);
  }

  calculateSumQuantity(value) {
    const numbers = value.split('+').map(num => parseFloat(num.trim()));
    return numbers.reduce((acc, curr) => acc + curr, 0);
  }

  hideLine(row: OrderLine) {
    this.hideLineEmiter.emit(row);
  }

  ShowHidenLines() {
    this.hideLinesEmiter.emit();
  }

  validateNewQuantity(event, id) {
    const match = event.match(this.SAISIE_PATTERN);
    if (!event || event.trim() === '') {
      document.getElementById(id).classList.remove('error-saisie');
      return true;
    } else if (match && event.replace(/\+/g, '').trim() !== '') {
      document.getElementById(id).classList.remove('error-saisie');
      return true;
    } else {
      document.getElementById(id).classList.add('error-saisie');
      return false;
    }
  }

  editLineText(row: OrderLine) {
    this.editTextLineEmiter.emit(row);
  }
}
