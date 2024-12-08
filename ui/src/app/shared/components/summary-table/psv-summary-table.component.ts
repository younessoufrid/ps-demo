import {Component, EventEmitter, Input, OnInit, Output, SimpleChanges} from '@angular/core';
import {ColumnMode} from '@swimlane/ngx-datatable';
import {SummaryOrderLines} from '../../models/beans';


@Component({
  selector: 'psv-summary-table',
  templateUrl: './psv-summary-table.component.html',
  styleUrls: ['./psv-summary-table.component.scss']
})
export class PsvSummaryTableComponent implements OnInit {
  @Input() columns: any[];
  @Input() rows: SummaryOrderLines[];
  @Input() headerHeight: number;
  @Input() footerHeight: number;
  @Input() rowHeight: number;
  @Input() scrollbarH = false;
  @Input() scrollbarV = false;
  @Input() totalRows: number;
  @Input() currentPage: number;
  @Input() rowsPerPage: number;
  @Input()  days = [];
  @Input() expend;
  @Output() pageChange: EventEmitter<number> = new EventEmitter();

  ColumnMode = ColumnMode;


  constructor() {
  }

  ngOnInit(): void {
  }


  onPage(event) {
    if (event && this.currentPage !== event) {
      this.pageChange.emit(event.page);
      this.currentPage = event.page;
    }
  }
}
