import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';


import { pairwise, startWith} from 'rxjs/operators';
import {OrderFilters, SimpleSelectItem, YMDDate} from '../../models/beans';
import {ORDER_LIST_FILTER_FORM} from '../../forms/forms-model';
import {formatDateDMY, getMaxDateAsCurrentMonth, getMinDateAsCurrentMonth, getTodayDate} from '../../utils/dateUtils';

@Component({
  selector: 'custom-filter-saisie',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.scss']
})
export class FilterComponent implements OnInit {
  @Output() searchByFilters: EventEmitter<any> = new EventEmitter<any>();
  // list of filters data
  @Input() saleSites: SimpleSelectItem[] = [];
  @Input() cdrs: SimpleSelectItem[] = [];
  @Input() clients: SimpleSelectItem[] = [];
  @Input() affairs: SimpleSelectItem[] = [];
  @Input() orders: SimpleSelectItem[] = [];
  @Input() isSummary: boolean;
  formGroup: FormGroup;
  filterData: OrderFilters;
  maxDate: YMDDate;
  minDate: YMDDate;
  today: YMDDate;

  constructor(private fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.formGroup = this.fb.group(ORDER_LIST_FILTER_FORM);
    this.formGroup.patchValue({saleSite: null, cdr: null , client: null, affair: null, order: null});
    this.initFilterLists();
    this.today = getTodayDate();
    this.maxDate = getMaxDateAsCurrentMonth();
    this.minDate = getMinDateAsCurrentMonth();
    this.formGroup = this.fb.group(ORDER_LIST_FILTER_FORM);
    this.formGroup.patchValue({saleSite: null, cdr: null , client: null, affair: null, order: null});
    this.formGroup.get('date').setValue(new Date());
    // distinctUntilChanged(), throttleTime(500)
    this.formGroup.valueChanges.pipe(startWith(null), pairwise()).subscribe(([old, current]) => {
      this.filterChanged(old, current);
    });
    this.searchByFilters.emit({cdr: null, client: null, affair: null, order: null, date: formatDateDMY(this.formGroup.get('date').value)});
  }

  filterChanged(old, current) {
    this.filterData.date = formatDateDMY(current.date);
    if (old?.saleSite !== current?.saleSite) {
        this.initFilterLists();
        this.filterData.saleSite = current.saleSite;
        this.resetCdrClientAffairOrder();
        this.emitFilters()
    } else if (old?.cdr !== current?.cdr) {
      this.filterData.cdr = current.cdr;
      this.resetClientAffairOrder();
      this.emitFilters()
    } else if (old?.client !== current?.client) {
      this.filterData.client = current.client;
      if (current.client === null) {
        this.resetClientAffairOrder();
      }
      if (this.clients.length !== 1 || this.isNotClientAffairOrder()) {
        this.emitFilters()
      }
    } else if (old?.affair !== current?.affair ) {
      this.filterData.affair = current.affair;
      if (current.affair === null) {
        this.resetClientAffairOrder();
      }
      if (this.affairs.length !== 1 || this.isNotClientAffairOrder()) {
        this.emitFilters()
      }
    } else if (old?.order !== current?.order) {
      this.filterData.order = current.order;
      if (this.orders.length !== 1 || this.isNotClientAffairOrder()) {
        this.emitFilters()
      }
      if (current?.order === null) {
        this.resetClientAffairOrder();
      }
    } else if (old?.date !== current?.date) {
      this.filterData.date = formatDateDMY(current.date);
      this.emitFilters()
    }
  }
  initFilterLists() {
    // init filters
    this.filterData = {saleSite: null, cdr: null, client: null, affair: null, order: null, date: null, action: null};
    this.saleSites = [];
    this.cdrs = [];
    this.clients = [];
    this.affairs = [];
    this.orders = [];
  }
  resetClientAffairOrder() {
    this.filterData.client = this.filterData.affair = this.filterData.order = null;
    this.formGroup.patchValue({client: null, affair: null, order: null});
  }
  resetCdrClientAffairOrder() {
    this.filterData.cdr = this.filterData.affair = this.filterData.client = this.filterData.order = null;
    this.formGroup.patchValue({cdr: null, client: null, affair: null, order: null});
  }
  emitFilters() {
    this.searchByFilters.emit(this.filterData);
  }
  isNotClientAffairOrder() {
    return !this.filterData.client && !this.filterData.affair && !this.filterData.order;
  }
}
